package com.dkm.backpack.service.impl;

import com.dkm.backpack.dao.BackpackMapper;
import com.dkm.backpack.dao.EquipmentMapper;
import com.dkm.backpack.entity.EquipmentEntity;
import com.dkm.backpack.entity.vo.EquipmentVo;
import com.dkm.backpack.entity.vo.UserEquipmentVo;
import com.dkm.backpack.service.IEquipmentService;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: game_project
 * @description: 装备信息
 * @author: zhd
 * @create: 2020-07-18 09:19
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class EquipmentServiceImpl implements IEquipmentService {

    @Resource
    private EquipmentMapper equipmentMapper;

    @Resource
    private BackpackMapper backpackMapper;

    @Override
    public EquipmentVo getEquipmentInfo(Long backpackId) {
        return equipmentMapper.getEquipmentInfo(backpackId);
    }

    @Override
    public List<UserEquipmentVo> getUserEquipment(Long userId) {
        List<UserEquipmentVo> userEquipment = equipmentMapper.getUserEquipment(userId);
        ArrayList<UserEquipmentVo> userEquipmentVos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            List<UserEquipmentVo> collect = userEquipment.stream().filter(a -> a.getEqType() == (finalI + 1)).collect(Collectors.toList());
            userEquipmentVos.add((collect==null||collect.size()==0)?null:collect.get(0));
        }
        return userEquipmentVos;
    }

    @Override
    public void removeOrEquipment(Long userId, Long backpackId) {
        EquipmentEntity equipmentEntity = equipmentMapper.selectById(backpackId);
        if(equipmentEntity==null){throw new ApplicationException(CodeType.SERVICE_ERROR,"找不到该装备");}
        if(equipmentEntity.getIsEquip()==1){
            equipmentEntity.setIsEquip(0);
            int i = equipmentMapper.updateById(equipmentEntity);
            if(i<=0){throw new ApplicationException(CodeType.SERVICE_ERROR,"暂时无法卸下该装备");}
        }else{
            EquipmentEntity equipId = equipmentMapper.getEquipId(userId, equipmentEntity.getEqType());
            if(equipId!=null){
                equipId.setIsEquip(0);
                if(equipmentMapper.updateById(equipId)<=0){throw new ApplicationException(CodeType.SERVICE_ERROR,"更新装备失败");}
            }else {
                equipmentEntity.setIsEquip(1);
                if(equipmentMapper.updateById(equipmentEntity)<=0){throw new ApplicationException(CodeType.SERVICE_ERROR,"更新装备失败");}
            }
        }
    }
}
