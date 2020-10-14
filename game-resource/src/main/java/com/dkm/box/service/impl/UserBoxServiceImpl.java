package com.dkm.box.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dkm.backpack.dao.BackpackMapper;
import com.dkm.backpack.dao.EquipmentMapper;
import com.dkm.backpack.dao.GoodsMapper;
import com.dkm.backpack.entity.BackPackEntity;
import com.dkm.backpack.entity.EquipmentEntity;
import com.dkm.backpack.entity.GoodsEntity;
import com.dkm.backpack.entity.vo.OpenEquipmentVo;
import com.dkm.backpack.entity.vo.UserEquipmentVo;
import com.dkm.backpack.service.IBackpackService;
import com.dkm.box.dao.AutoSellMapper;
import com.dkm.box.entity.AutoSellEntity;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.box.dao.UserBoxMapper;
import com.dkm.box.entity.UserBoxEntity;
import com.dkm.box.entity.vo.BoxInfoVo;
import com.dkm.box.service.IUserBoxService;
import com.dkm.utils.DateUtils;
import com.dkm.utils.IdGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-07-17 14:30
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class UserBoxServiceImpl implements IUserBoxService {

    @Resource
    private UserBoxMapper userBoxMapper;

    @Resource
    private IdGenerator idGenerator;

    @Resource
    private BackpackMapper backpackMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private EquipmentMapper equipmentMapper;

    @Resource
    private AutoSellMapper autoSellMapper;

    @Override
    public List<BoxInfoVo> getBoxInfo(Long userId) {
        //获取用户宝箱
        List<BoxInfoVo> boxInfoVos = userBoxMapper.selectBoxById(userId);
        LocalDateTime now = LocalDateTime.now();
        if(boxInfoVos==null||boxInfoVos.size()==0){
            //无记录 新增记录
            ArrayList<UserBoxEntity> list = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                //8个宝箱
                UserBoxEntity userBoxEntity = new UserBoxEntity();
                userBoxEntity.setBoxId(idGenerator.getNumberId());
                userBoxEntity.setUserId(userId);
                userBoxEntity.setOpenTime(now);
                //两个普通宝箱 6个2级宝箱
                userBoxEntity.setBoxLevel(i>1?2:1);
                list.add(userBoxEntity);
            }
            Integer integer = userBoxMapper.insertList(list);
            if(integer<=0) {throw  new ApplicationException(CodeType.SERVICE_ERROR,"初始化宝箱失败");}
            //转化前端信息
            list.forEach(userBoxEntity-> boxInfoVos.add(new BoxInfoVo(userBoxEntity.getBoxId(),null, DateUtils.formatDateTime(now),userBoxEntity.getBoxLevel(),1)));
            return boxInfoVos;
        }
        boxInfoVos.forEach(boxInfoVo -> {
            boxInfoVo.setStatus(now.isAfter(boxInfoVo.getOpenTime())?1:0);
            boxInfoVo.setTime(DateUtils.formatDateTime(boxInfoVo.getOpenTime()));
        });
        return boxInfoVos;
    }

    @Override
    public List<OpenEquipmentVo> openBox(Long userId, Long boxId) {
        //得到当前时间
        LocalDateTime now = LocalDateTime.now();
        //得到剩余的次数
        int backpackNumber =30 - backpackMapper.getBackpackNumber(userId);
        if (backpackNumber<=0){throw new ApplicationException(CodeType.SERVICE_ERROR,"背包空间不足");}
        Random random = new Random();
        //得到类型为1的物品信息
        List<GoodsEntity> goodsEntities = goodsMapper.selectList(new LambdaQueryWrapper<GoodsEntity>()
              .eq(GoodsEntity::getGoodType, 1));
        //根据用户id查询自动出售的信息
        AutoSellEntity autoSellEntity = autoSellMapper.selectById(userId);
        Integer userGrade = userBoxMapper.getUserGrade(userId);
        List<OpenEquipmentVo> result = new ArrayList<>();
        if(boxId==0){
            //得到宝箱信息的集合
            List<UserBoxEntity> userBoxEntities = userBoxMapper.selectList(new LambdaQueryWrapper<UserBoxEntity>()
                    .eq(UserBoxEntity::getUserId, userId).le(UserBoxEntity::getOpenTime,now));
            //判断是否有宝箱可以开
            if(userBoxEntities==null||userBoxEntities.size()==0){throw new ApplicationException(CodeType.SERVICE_ERROR,"没有宝箱可以开");}
            if(userBoxEntities.size()>backpackNumber){
                userBoxEntities = userBoxEntities.subList(0,backpackNumber);
            }
            List<BackPackEntity> backPackEntities = new ArrayList<>();
            List<EquipmentEntity> equipmentEntities = new ArrayList<>();
            for (int i = 0; i < userBoxEntities.size(); i++) {
                //得到宝箱等级
                Integer boxLevel = userBoxEntities.get(i).getBoxLevel();
                if(boxLevel==1){
                    //1级的数据
                    userBoxEntities.get(i).setOpenTime(now.minusMinutes(-20));
                }else{
                    //其他级的数据
                    userBoxEntities.get(i).setOpenTime(now.minusMinutes(-50));
                }
                //得到物品的信息
                GoodsEntity goodsEntity = goodsEntities.get(random.nextInt(goodsEntities.size()));
                BackPackEntity backPackEntity = new BackPackEntity();
                backPackEntity.setBackpackId(idGenerator.getNumberId());
                backPackEntity.setGoodId(goodsEntity.getId());
                backPackEntity.setNumber(1);
                backPackEntity.setUserId(userId);
                //得到装备信息
                EquipmentEntity equipmentEntity = setEquipmentEntity(goodsEntity, backPackEntity, userGrade, boxLevel, random);
                boolean sell = autoSellEntity == null || !autoSellEntity.getAutoSellOrder().contains(equipmentEntity.getNeedGrade() / 5 + 1 + "");
                if(sell){
                    backPackEntities.add(backPackEntity);
                    equipmentEntities.add(equipmentEntity);
                }
                //开箱返回装备信息
                OpenEquipmentVo openEquipmentVo = new OpenEquipmentVo();
                openEquipmentVo.setBackpackId(backPackEntity.getBackpackId());
                openEquipmentVo.setUrl(goodsEntity.getUrl());
                openEquipmentVo.setIsAutoSell(sell?0:1);
                result.add(openEquipmentVo);
            }
            if(backPackEntities!=null&&backPackEntities.size()!=0
                &&equipmentEntities!=null&&equipmentEntities.size()!=0){
                //开启宝箱
                Integer integer = backpackMapper.insertList(backPackEntities);
                Integer integer1 = equipmentMapper.insertList(equipmentEntities);
                if(integer!=backPackEntities.size()||integer1!=equipmentEntities.size()){
                    throw new ApplicationException(CodeType.SERVICE_ERROR,"开箱失败");
                }
            }
            //修改宝箱信息
            Integer integer2 = userBoxMapper.updateBoxTime(userBoxEntities);
            if(integer2!=userBoxEntities.size()){
                throw new ApplicationException(CodeType.SERVICE_ERROR,"开箱失败");
            }
        }else {
            //得到该用户的宝箱信息
            UserBoxEntity userBoxEntity = userBoxMapper.selectOne(new LambdaQueryWrapper<UserBoxEntity>()
                    .eq(UserBoxEntity::getUserId,userId)
                    .eq(UserBoxEntity::getBoxId, boxId)
                    .le(UserBoxEntity::getOpenTime,now));
            //判断是否开启宝箱
            if(userBoxEntity==null){throw new ApplicationException(CodeType.SERVICE_ERROR,"现在还不能开启该宝箱");}
            //添加时间
            userBoxEntity.setOpenTime(userBoxEntity.getBoxLevel()==1?now.minusMinutes(-20):now.minusMinutes(-50));
            GoodsEntity goodsEntity = goodsEntities.get(random.nextInt(goodsEntities.size()));
            //装配返回的类
            BackPackEntity backPackEntity = new BackPackEntity();
            backPackEntity.setBackpackId(idGenerator.getNumberId());
            backPackEntity.setGoodId(goodsEntity.getId());
            backPackEntity.setNumber(1);
            backPackEntity.setUserId(userId);
            //装配
            EquipmentEntity equipmentEntity = setEquipmentEntity(goodsEntity, backPackEntity, userGrade, userBoxEntity.getBoxLevel(), random);
            boolean sell = autoSellEntity != null && !autoSellEntity.getAutoSellOrder().contains(equipmentEntity.getNeedGrade() / 5 + 1 + "");
            //开箱返回装备信息
            OpenEquipmentVo openEquipmentVo = new OpenEquipmentVo();
            openEquipmentVo.setBackpackId(backPackEntity.getBackpackId());
            openEquipmentVo.setUrl(goodsEntity.getUrl());
            openEquipmentVo.setIsAutoSell(sell?0:1);
            result.add(openEquipmentVo);
            if(sell){
                //添加背包
                int insert = backpackMapper.insert(backPackEntity);
                //添加装备
                int insert1 = equipmentMapper.insert(equipmentEntity);
                if(insert<0||insert1<0){throw new ApplicationException(CodeType.SERVICE_ERROR,"开箱失败");}
            }
            int i = userBoxMapper.updateById(userBoxEntity);
            if(i<=0){throw new ApplicationException(CodeType.SERVICE_ERROR,"开箱失败");}
        }
        return result;
    }

    /**
     * 随机生成装备属性
     * @param goodsEntity
     * @param backPackEntity
     * @param userGrade
     * @param boxLevel
     * @param random
     * @return
     */
    EquipmentEntity setEquipmentEntity( GoodsEntity goodsEntity,BackPackEntity backPackEntity,Integer userGrade,Integer boxLevel,Random random ){
        EquipmentEntity equipmentEntity = new EquipmentEntity();
        int grade = (boxLevel + 1) + random.nextInt(boxLevel) - random.nextInt(boxLevel);
        equipmentEntity.setBackpackId(backPackEntity.getBackpackId());
        equipmentEntity.setBlood(userGrade * userGrade + userGrade * 50 + random.nextInt(userGrade * 50) - random.nextInt(userGrade * 50));
        equipmentEntity.setBloodAdd(new BigDecimal(grade / 100.00));
        equipmentEntity.setCrit(new BigDecimal(Math.abs(Math.max(userGrade-grade,1)) / 100.00));
        equipmentEntity.setNeedGrade(Math.max(userGrade + random.nextInt(5) - random.nextInt(5),1));
        equipmentEntity.setEqDrop(new BigDecimal(userGrade / 100.00));
        equipmentEntity.setRenown(userGrade * userGrade + userGrade * 100 + random.nextInt(userGrade * 100) - random.nextInt(userGrade * 100));
        equipmentEntity.setIsEquip(0);
        equipmentEntity.setTalent(userGrade * userGrade);
        equipmentEntity.setTalentAdd(new BigDecimal(userGrade / 100.00));
        equipmentEntity.setEqType(Integer.valueOf(goodsEntity.getGoodContent()));
        equipmentEntity.setExp(random.nextInt(8) + 1);
        equipmentEntity.setGrade(grade);
        //返回随机生成装备属性
        return equipmentEntity;
    }
}
