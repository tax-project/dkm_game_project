package com.dkm.backpack.service.impl;

import com.dkm.backpack.dao.BackpackMapper;
import com.dkm.backpack.dao.EquipmentMapper;
import com.dkm.backpack.entity.EquipmentEntity;
import com.dkm.backpack.entity.vo.EquipmentVo;
import com.dkm.backpack.entity.vo.UserEquipmentVo;
import com.dkm.backpack.service.IEquipmentService;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: game_project
 * @description: 装备信息
 * @author: zhd
 * @create: 2020-07-18 09:19
 **/
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class EquipmentServiceImpl implements IEquipmentService {

    @Resource
    private EquipmentMapper equipmentMapper;

    @Resource
    private BackpackMapper backpackMapper;

    @Override
    public Map<String, EquipmentVo> getEquipmentInfo(Long userId, Long backpackId) {
        //根据背包Id进行查询
        EquipmentEntity equipmentEntity = equipmentMapper.selectById(backpackId);
        Map<String, EquipmentVo> result = new HashMap<>(4);
        if(equipmentEntity==null){throw new ApplicationException(CodeType.SERVICE_ERROR,"找不到当前装备信息");}
        if ( equipmentEntity.getIsEquip() == 1) {
            //如果是装备
            result.put("nowEquip", equipmentMapper.getEquipmentInfo(backpackId));
        } else {
            //不是装备
            result.put("selectEquip", equipmentMapper.getEquipmentInfo(backpackId));
            result.put("nowEquip", equipmentMapper.getEquipingInfo(userId, equipmentEntity.getEqType()));
        }
        return result;
    }

    @Override
    public List<UserEquipmentVo> getUserEquipment(Long userId) {
        //根据用户id查询装备信息
        List<UserEquipmentVo> userEquipment = equipmentMapper.getUserEquipment(userId);
        ArrayList<UserEquipmentVo> userEquipmentVos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            List<UserEquipmentVo> collect = userEquipment.stream()
                  .filter(a -> a.getEqType() == (finalI + 1)).collect(Collectors.toList());
            //添加进装配的集合中
            userEquipmentVos.add((collect == null || collect.size() == 0) ? null : collect.get(0));
        }
        return userEquipmentVos;
    }

    @Override
    public void removeOrEquipment(Long userId, Long backpackId) {
        //根据背包id查询装备信息
        EquipmentEntity equipmentEntity = equipmentMapper.selectById(backpackId);
        if (equipmentEntity == null) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "找不到该装备");
        }
        if (equipmentEntity.getIsEquip() == 1) {
            //如果是装备
            equipmentEntity.setIsEquip(0);
            //根据用户id查询背包数量
            Integer backpackNumber = backpackMapper.getBackpackNumber(userId);
            //背包最大为30
            if(backpackNumber>=30){ throw new ApplicationException(CodeType.SERVICE_ERROR, "背包已满");}
            //否则就修改
            int i = equipmentMapper.updateById(equipmentEntity);
            if (i <= 0) {
                throw new ApplicationException(CodeType.SERVICE_ERROR, "暂时无法卸下该装备");
            }
        } else {
            //得到背包数据
            EquipmentEntity equipId = equipmentMapper.getEquipId(userId, equipmentEntity.getEqType());
            if (equipId != null) {
                equipId.setIsEquip(0);
                //更新背包
                if (equipmentMapper.updateById(equipId) <= 0) {
                    throw new ApplicationException(CodeType.SERVICE_ERROR, "更新装备失败");
                }
            }
            equipmentEntity.setIsEquip(1);
            //更新背包·
            if (equipmentMapper.updateById(equipmentEntity) <= 0) {
                throw new ApplicationException(CodeType.SERVICE_ERROR, "更新装备失败");
            }
        }
    }

    @Override
    public EquipmentEntity getUserAllEquipment(Long userId) {
        return equipmentMapper.getAllAttributes(userId);
    }
}
