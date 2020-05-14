package com.dkm.knapsack.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.blackHouse.domain.TbBlackHouse;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.knapsack.dao.TbEquipmentMapper;
import com.dkm.knapsack.domain.TbEquipment;
import com.dkm.knapsack.domain.vo.TbEquipmentVo;
import com.dkm.knapsack.service.ITbEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 装备表 服务实现类
 * </p>
 *
 * @author zy
 * @since 2020-05-12
 */
@Service
public class TbEquipmentServiceImpl implements ITbEquipmentService {
    @Autowired
    TbEquipmentMapper tbEquipmentMapper;


    @Override
    public List<TbEquipmentVo> selectByKnapsackId(TbEquipmentVo tbEquipmentVo) {
        return tbEquipmentMapper.selectByKnapsackId(tbEquipmentVo);
    }


    @Override
    public void updateExp1(Long equipmentId) {
        TbEquipment tbEquipment=new TbEquipment();
        tbEquipment.setExp1("0");
        QueryWrapper<TbEquipment> queryWrapper=new QueryWrapper();
        queryWrapper.eq("equipment_id",equipmentId);
        int rows=tbEquipmentMapper.update(tbEquipment,queryWrapper);
        if(rows <= 0){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "卸下装备失败!");
        }
    }


}
