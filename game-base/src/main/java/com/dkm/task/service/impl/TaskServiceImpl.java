package com.dkm.task.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.ResourceFeignClient;
import com.dkm.feign.entity.AddGoodsInfo;
import com.dkm.task.dao.TaskMapper;
import com.dkm.task.dao.TaskUserMapper;
import com.dkm.task.entity.TaskEntity;
import com.dkm.task.entity.TaskUserEntity;
import com.dkm.task.entity.vo.GoodList;
import com.dkm.task.entity.vo.GoodListImg;
import com.dkm.task.entity.vo.TaskUserDetailVo;
import com.dkm.task.service.TaskService;
import com.dkm.turntable.dao.GoodsDao;
import com.dkm.turntable.entity.GoodsEntity;
import com.dkm.utils.IdGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: game_project
 * @description: 任务
 * @author: zhd
 * @create: 2020-06-08 15:00
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class TaskServiceImpl implements TaskService {

    @Resource
    private TaskMapper taskMapper;
    @Resource
    private TaskUserMapper taskUserMapper;
    @Resource
    private ResourceFeignClient resourceFeignClient;
    @Resource
    private GoodsDao goodsDao;
    @Resource
    private IdGenerator idGenerator;

    @Override
    public List<TaskUserDetailVo> selectUserTask(Long userId, Integer type) {
        //得到当前时间
        LocalDate today = LocalDate.now();
        //根据类型和用户id查询所有数据
        List<TaskUserDetailVo> taskUserDetailVos = taskMapper.selectUserTask(type, userId);
        //查询信息
        Map<Long, String> collect = goodsDao.selectList(new LambdaQueryWrapper<GoodsEntity>()
              .ne(GoodsEntity::getGoodType, 1))
              .stream().collect(Collectors.toMap(GoodsEntity::getId, GoodsEntity::getUrl));
        //对taskUserDetailVos循环
        taskUserDetailVos.forEach(taskUserDetailVo -> {
            //摔选出日常的数据
            if(taskUserDetailVo.getTime()!=null&&!today.isEqual(taskUserDetailVo.getTime())&&taskUserDetailVo.getTaskType()==1){
                taskUserDetailVo.setComplete(0);
                taskUserDetailVo.setTuProcess(0);
            }
            List<GoodList> goodLists = JSON.parseArray(taskUserDetailVo.getGoodList(), GoodList.class);
            List<GoodListImg> goodListImg = new ArrayList<>();
            goodLists.forEach(goodList -> {
                GoodListImg goodImg = new GoodListImg();
                goodImg.setUrl(collect.get(goodList.getGoodId()));
                goodImg.setNumber(goodList.getNumber());
                //装配集合
                goodListImg.add(goodImg);
            });
            taskUserDetailVo.setGoodListImg(goodListImg);
        });
        //返回数据
        return taskUserDetailVos;
    }

    @Override
    public void getTaskReward(Long userId, Long taskId) {
        //获取任务信息
        TaskEntity taskEntity = taskMapper.selectById(taskId);
        if(taskEntity==null){
            throw new ApplicationException(CodeType.RESOURCES_NOT_FIND,"不存在该任务");
        }
        //根据用户id和任务Id查询到一条数据
        TaskUserEntity taskUserEntity = taskUserMapper.selectOne(new LambdaQueryWrapper<TaskUserEntity>()
                .eq(TaskUserEntity::getTaskId, taskId)
                .eq(TaskUserEntity::getUserId, userId));
        if(taskUserEntity==null||taskUserEntity.getTuProcess()<taskEntity.getTaskProcess()){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"还未完成该任务");
        }
        if(taskUserEntity.getComplete()==1){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"已领取过奖励");
        }
        //json转换
        List<GoodList> goodLists = JSON.parseArray(taskEntity.getGoodList(), GoodList.class);
        //将集合转换成map
        Map<Long, Integer> taskGoE = goodLists.stream().collect(Collectors.toMap(GoodList::getGoodId, GoodList::getNumber));
        //得到集合信息
        List<Long> collect = goodLists.stream().mapToLong(GoodList::getGoodId).boxed().collect(Collectors.toList());
        //根据得到的集合信息进行查询数据库
        List<GoodsEntity> goodsEntities = goodsDao.selectBatchIds(collect);
        //过滤掉金币和经验的数据进行装配
        Map<String, Long> goodGoE = goodsEntities.stream()
                .filter(goodsEntity -> "金币".equals(goodsEntity.getName()) || "经验".equals(goodsEntity.getName()))
                .collect(Collectors.toMap(GoodsEntity::getName, GoodsEntity::getId));
        if(!goodGoE.isEmpty()){
            int exp = 0;
            int gold= 0;
            if(goodGoE.get("金币")!=null){
                Long g = goodGoE.get("金币");
                gold=taskGoE.get(g);
                goodLists = goodLists.stream().filter(a-> !a.getGoodId().equals(g)).collect(Collectors.toList());
            }
            if(goodGoE.get("经验")!=null){
                Long e = goodGoE.get("经验");
                exp=taskGoE.get(e);
                goodLists = goodLists.stream().filter(a-> !a.getGoodId().equals(e)).collect(Collectors.toList());
            }
            //更新任务所需物品
            taskMapper.updateUserInfo(gold,exp);
        }
        if(goodLists!=null&&goodLists.size()!=0){
            AddGoodsInfo addGoodsInfo = new AddGoodsInfo();
            addGoodsInfo.setUserId(userId);
            goodLists.forEach(a->{
                addGoodsInfo.setGoodId(a.getGoodId());
                addGoodsInfo.setNumber(a.getNumber());
                //添加进物品信息
                //通过feign
                resourceFeignClient.addBackpackGoods(addGoodsInfo);
            });
        }
        taskUserEntity.setComplete(1);
        //修改任务
        if(taskUserMapper.updateById(taskUserEntity)<=0){
            throw  new ApplicationException(CodeType.SERVICE_ERROR);
        };
    }

    @Override
    public void setTaskProcess(Long userId, Long taskId) {
        //获取任务信息
        TaskEntity taskEntity = taskMapper.selectById(taskId);
        if(taskEntity!=null){
            //得到当前时间
            LocalDate today = LocalDate.now();
            //根据用户和任务Id查询到数据库信息
            TaskUserEntity taskUserEntity = taskUserMapper.selectOne(new LambdaQueryWrapper<TaskUserEntity>()
                    .eq(TaskUserEntity::getTaskId, taskId)
                    .eq(TaskUserEntity::getUserId, userId));
            if(taskUserEntity!=null){
                if(taskUserEntity.getComplete()!=1&&taskEntity.getTaskType()!=1){
                    //如果没有完成任务
                    taskUserEntity.setTuProcess(taskUserEntity.getTuProcess()+1);
                }else if(taskEntity.getTaskType()==1){
                    //如果已经完成任务
                    if(today.isAfter(taskUserEntity.getTime())){
                        taskUserEntity.setTuProcess(1);
                        taskUserEntity.setTime(today);
                        taskUserEntity.setComplete(0);
                    }else if(today.isEqual(taskUserEntity.getTime())&&taskUserEntity.getComplete()!=1){
                        taskUserEntity.setTuProcess(taskUserEntity.getTuProcess()+1);
                    }
                }
                //修改任务
                taskUserMapper.updateById(taskUserEntity);
            }else {
                TaskUserEntity taskUserEntity1 = new TaskUserEntity();
                taskUserEntity1.setTime(today);
                taskUserEntity1.setTuProcess(1);
                taskUserEntity1.setTaskId(taskId);
                taskUserEntity1.setUserId(userId);
                taskUserEntity1.setTuId(idGenerator.getNumberId());
                taskUserEntity1.setComplete(0);
                //添加任务
                taskUserMapper.insert(taskUserEntity1);
            }

        }
    }
}
