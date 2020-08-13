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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class TaskServiceImpl implements TaskService {

    @Resource
    private TaskMapper taskMapper;
    @Resource
    private TaskUserMapper taskUserMapper;
    @Resource
    private ResourceFeignClient resourceFeignClient;
    @Resource
    private GoodsDao goodsDao;

    @Override
    public List<TaskUserDetailVo> selectUserTask(Long userId, Integer type) {
        List<TaskUserDetailVo> taskUserDetailVos = taskMapper.selectUserTask(type, userId);
        Map<Long, String> collect = goodsDao.selectList(new LambdaQueryWrapper<GoodsEntity>().ne(GoodsEntity::getGoodType, 1))
                .stream().collect(Collectors.toMap(GoodsEntity::getId, GoodsEntity::getUrl));
        GoodListImg goodImg = new GoodListImg();
        taskUserDetailVos.forEach(taskUserDetailVo -> {
            List<GoodList> goodLists = JSON.parseArray(taskUserDetailVo.getGoodList(), GoodList.class);
            List<GoodListImg> goodListImg = new ArrayList<>();
            goodLists.forEach(goodList -> {
                goodImg.setUrl(collect.get(goodList.getGoodId()));
                goodImg.setNumber(goodList.getNumber());
            });
            taskUserDetailVo.setGoodListImg(goodListImg);
        });
        return taskUserDetailVos;
    }

    @Override
    public void getTaskReward(Long userId, Long taskId) {
        //获取任务信息
        TaskEntity taskEntity = taskMapper.selectById(taskId);
        if(taskEntity==null){
            throw new ApplicationException(CodeType.RESOURCES_NOT_FIND,"不存在该任务");
        }
        TaskUserEntity taskUserEntity = taskUserMapper.selectOne(new LambdaQueryWrapper<TaskUserEntity>()
                .eq(TaskUserEntity::getTaskId, taskId)
                .eq(TaskUserEntity::getUserId, userId));
        if(taskUserEntity==null||taskUserEntity.getTuProcess()<taskEntity.getTaskProcess()){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"还未完成该任务");
        }
        if(taskUserEntity.getComplete()==1){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"已领取过奖励");
        }
        List<GoodList> goodLists = JSON.parseArray(taskEntity.getGoodList(), GoodList.class);
        Map<Long, Integer> taskGoE = goodLists.stream().collect(Collectors.toMap(GoodList::getGoodId, GoodList::getNumber));
        List<Long> collect = goodLists.stream().mapToLong(GoodList::getGoodId).boxed().collect(Collectors.toList());
        List<GoodsEntity> goodsEntities = goodsDao.selectBatchIds(collect);
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
                resourceFeignClient.addBackpackGoods(addGoodsInfo);
            });
        }
    }
}
