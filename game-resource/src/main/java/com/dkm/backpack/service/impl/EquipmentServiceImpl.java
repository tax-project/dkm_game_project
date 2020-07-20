package com.dkm.backpack.service.impl;

import com.dkm.backpack.dao.EquipmentMapper;
import com.dkm.backpack.entity.vo.EquipmentVo;
import com.dkm.backpack.entity.vo.UserEquipmentVo;
import com.dkm.backpack.service.IEquipmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public EquipmentVo getEquipmentInfo(Long backpackId) {
        return equipmentMapper.getEquipmentInfo(backpackId);
    }

    @Override
    public List<UserEquipmentVo> getUserEquipment(Long userId) {
        return equipmentMapper.getUserEquipment(userId);
    }
}
