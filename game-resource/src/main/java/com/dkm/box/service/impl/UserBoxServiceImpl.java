package com.dkm.box.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dkm.backpack.dao.BackpackMapper;
import com.dkm.backpack.dao.GoodsMapper;
import com.dkm.backpack.entity.BackPackEntity;
import com.dkm.backpack.entity.GoodsEntity;
import com.dkm.backpack.service.IBackpackService;
import com.dkm.constanct.CodeType;
import com.dkm.equipment.dao.EquipmentMapper;
import com.dkm.equipment.entity.EquipmentEntity;
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
        if(boxId==0){
            List<UserBoxEntity> userBoxEntities = userBoxMapper.selectList(new LambdaQueryWrapper<UserBoxEntity>()
                    .eq(UserBoxEntity::getUserId, userId).le(UserBoxEntity::getOpenTime,now));
            if(userBoxEntities==null||userBoxEntities.size()==0){throw new ApplicationException(CodeType.SERVICE_ERROR,"没有宝箱可以开");}
            if(userBoxEntities.size()>backpackNumber){
                userBoxEntities = userBoxEntities.subList(0,backpackNumber);
            }
            List<Long> ids = userBoxEntities.stream().mapToLong(UserBoxEntity::getBoxId).boxed().collect(Collectors.toList());
            List<GoodsEntity> goodsEntities = goodsMapper.selectList(new LambdaQueryWrapper<GoodsEntity>().eq(GoodsEntity::getGoodType, 1));
            List<BackPackEntity> backPackEntities = new ArrayList<>();
            List<EquipmentEntity> equipmentEntities = new ArrayList<>();
            Random random = new Random();
            Integer userGrade = userBoxMapper.getUserGrade(userId);
            for (int i = 0; i < userBoxEntities.size(); i++) {
                Integer boxLevel = userBoxEntities.get(i).getBoxLevel();
                if(userBoxEntities.get(i).getBoxLevel()==1){
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
                EquipmentEntity equipmentEntity = new EquipmentEntity();
                equipmentEntity.setBackpackId(backPackEntity.getBackpackId());
                equipmentEntity.setBlood(userGrade * userGrade + userGrade * 50 + random.nextInt(userGrade * 50) - random.nextInt(userGrade * 50));
                equipmentEntity.setBloodAdd(new BigDecimal(userGrade / 100.00));
                equipmentEntity.setCrit(new BigDecimal(userGrade / 100.00));
                equipmentEntity.setNeedGrade(userGrade + random.nextInt(5) - random.nextInt(5));
                equipmentEntity.setEqDrop(new BigDecimal(userGrade / 100.00));
                equipmentEntity.setRenown(userGrade * userGrade + userGrade * 100 + random.nextInt(userGrade * 100) - random.nextInt(userGrade * 100));
                equipmentEntity.setIsEquip(0);
                equipmentEntity.setTalent(userGrade * userGrade);
                equipmentEntity.setTalentAdd(new BigDecimal(userGrade / 100.00));
                equipmentEntity.setEqType(Integer.valueOf(goodsEntity.getGoodContent()));
                equipmentEntity.setExp(random.nextInt(8) + 1);
                equipmentEntity.setGrade((boxLevel + 1) + random.nextInt(boxLevel) - random.nextInt(boxLevel));
                equipmentEntities.add(equipmentEntity);
            }
            Integer integer = backpackMapper.insertList(backPackEntities);
            Integer integer1 = equipmentMapper.insertList(equipmentEntities);
            Integer integer2 = userBoxMapper.updateBoxTime(now.minusMinutes(-15));
            if(!integer.equals(integer1)||!integer2.equals(integer)){throw new ApplicationException(CodeType.SERVICE_ERROR,"开箱失败");}
        }
    }
}
