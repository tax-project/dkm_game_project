package com.dkm.box.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dkm.backpack.dao.BackpackMapper;
import com.dkm.backpack.dao.EquipmentMapper;
import com.dkm.backpack.dao.GoodsMapper;
import com.dkm.backpack.entity.BackPackEntity;
import com.dkm.backpack.entity.EquipmentEntity;
import com.dkm.backpack.entity.GoodsEntity;
import com.dkm.backpack.service.IBackpackService;
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
    public void openBox(Long userId,Long boxId) {
        LocalDateTime now = LocalDateTime.now();
        int backpackNumber =30 - backpackMapper.getBackpackNumber(userId);
        if (backpackNumber<=0){throw new ApplicationException(CodeType.SERVICE_ERROR,"背包空间不足");}
        Random random = new Random();
        List<GoodsEntity> goodsEntities = goodsMapper.selectList(new LambdaQueryWrapper<GoodsEntity>().eq(GoodsEntity::getGoodType, 1));
        Integer userGrade = userBoxMapper.getUserGrade(userId);
        if(boxId==0){
            List<UserBoxEntity> userBoxEntities = userBoxMapper.selectList(new LambdaQueryWrapper<UserBoxEntity>()
                    .eq(UserBoxEntity::getUserId, userId).le(UserBoxEntity::getOpenTime,now));
            if(userBoxEntities==null||userBoxEntities.size()==0){throw new ApplicationException(CodeType.SERVICE_ERROR,"没有宝箱可以开");}
            if(userBoxEntities.size()>backpackNumber){
                userBoxEntities = userBoxEntities.subList(0,backpackNumber);
            }
            List<BackPackEntity> backPackEntities = new ArrayList<>();
            List<EquipmentEntity> equipmentEntities = new ArrayList<>();
            for (int i = 0; i < userBoxEntities.size(); i++) {
                Integer boxLevel = userBoxEntities.get(i).getBoxLevel();
                if(boxLevel==1){
                    userBoxEntities.get(i).setOpenTime(now.minusMinutes(-20));
                }else{
                    userBoxEntities.get(i).setOpenTime(now.minusMinutes(-50));
                }
                GoodsEntity goodsEntity = goodsEntities.get(random.nextInt(goodsEntities.size()));
                BackPackEntity backPackEntity = new BackPackEntity();
                backPackEntity.setBackpackId(idGenerator.getNumberId());
                backPackEntity.setGoodId(goodsEntity.getId());
                backPackEntity.setNumber(1);
                backPackEntity.setUserId(userId);
                backPackEntities.add(backPackEntity);
                equipmentEntities.add(setEquipmentEntity(goodsEntity,backPackEntity,userGrade,boxLevel,random));
            }
            Integer integer = backpackMapper.insertList(backPackEntities);
            Integer integer1 = equipmentMapper.insertList(equipmentEntities);
            Integer integer2 = userBoxMapper.updateBoxTime(userBoxEntities);
            if(!integer.equals(integer1)||!integer2.equals(integer)){throw new ApplicationException(CodeType.SERVICE_ERROR,"开箱失败");}
        }else {
            UserBoxEntity userBoxEntity = userBoxMapper.selectOne(new LambdaQueryWrapper<UserBoxEntity>()
                    .eq(UserBoxEntity::getUserId,userId).eq(UserBoxEntity::getBoxId, boxId).le(UserBoxEntity::getOpenTime,now));
            if(userBoxEntity==null){throw new ApplicationException(CodeType.SERVICE_ERROR,"现在还不能开启该宝箱");}
            userBoxEntity.setOpenTime(userBoxEntity.getBoxLevel()==1?now.minusMinutes(-20):now.minusMinutes(-50));
            GoodsEntity goodsEntity = goodsEntities.get(random.nextInt(goodsEntities.size()));
            BackPackEntity backPackEntity = new BackPackEntity();
            backPackEntity.setBackpackId(idGenerator.getNumberId());
            backPackEntity.setGoodId(goodsEntity.getId());
            backPackEntity.setNumber(1);
            backPackEntity.setUserId(userId);
            int insert = backpackMapper.insert(backPackEntity);
            int insert1 = equipmentMapper.insert(setEquipmentEntity(goodsEntity, backPackEntity, userGrade, userBoxEntity.getBoxLevel(), random));
            int i = userBoxMapper.updateById(userBoxEntity);
            if( insert!=insert1||insert1!=i){throw new ApplicationException(CodeType.SERVICE_ERROR,"开箱失败");}
        }
    }

    EquipmentEntity setEquipmentEntity( GoodsEntity goodsEntity,BackPackEntity backPackEntity,Integer userGrade,Integer boxLevel,Random random ){
        EquipmentEntity equipmentEntity = new EquipmentEntity();
        equipmentEntity.setBackpackId(backPackEntity.getBackpackId());
        equipmentEntity.setBlood(userGrade * userGrade + userGrade * 50 + random.nextInt(userGrade * 50) - random.nextInt(userGrade * 50));
        equipmentEntity.setBloodAdd(new BigDecimal(userGrade / 100.00));
        equipmentEntity.setCrit(new BigDecimal(userGrade / 100.00));
        equipmentEntity.setNeedGrade(Math.min(userGrade + random.nextInt(5) - random.nextInt(5),1));
        equipmentEntity.setEqDrop(new BigDecimal(userGrade / 100.00));
        equipmentEntity.setRenown(userGrade * userGrade + userGrade * 100 + random.nextInt(userGrade * 100) - random.nextInt(userGrade * 100));
        equipmentEntity.setIsEquip(0);
        equipmentEntity.setTalent(userGrade * userGrade);
        equipmentEntity.setTalentAdd(new BigDecimal(userGrade / 100.00));
        equipmentEntity.setEqType(Integer.valueOf(goodsEntity.getGoodContent()));
        equipmentEntity.setExp(random.nextInt(8) + 1);
        equipmentEntity.setGrade((boxLevel + 1) + random.nextInt(boxLevel) - random.nextInt(boxLevel));
        return equipmentEntity;
    }
}
