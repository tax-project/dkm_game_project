package com.dkm.knapsack.service.impl;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.knapsack.dao.TbEquipmentDetailsMapper;
import com.dkm.knapsack.dao.TbEquipmentMapper;
import com.dkm.knapsack.domain.TbEquipment;
import com.dkm.knapsack.domain.TbEquipmentDetails;
import com.dkm.knapsack.domain.vo.TbEquipmentVo;
import com.dkm.knapsack.service.ITbEquipmentService;
import com.dkm.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 装备表 服务实现类
 * </p>
 *
 * @author zy
 * @since 2020-05-14
 */
@Service
public class TbEquipmentServiceImpl implements ITbEquipmentService {
    @Autowired
    TbEquipmentMapper tbEquipmentMapper;
    @Autowired
    TbEquipmentDetailsMapper tbEquipmentDetailsMapper;
    @Autowired
    private IdGenerator idGenerator;

    @Override
    public void addTbEquipment(TbEquipmentVo tbEquipmentVo) {
        //给装备一个主键
        tbEquipmentVo.setEquipmentId(idGenerator.getNumberId());
        TbEquipment tbEquipment=new TbEquipment();
        BeanUtils.copyProperties(tbEquipmentVo,tbEquipment);
        int rows=tbEquipmentMapper.insert(tbEquipment);
        if(rows <= 0){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "增加失败");
        }else{
            TbEquipmentDetails tbEquipmentDetails=new TbEquipmentDetails();
            BeanUtils.copyProperties(tbEquipmentVo,tbEquipmentDetails);
            tbEquipmentDetails.setEdId(idGenerator.getNumberId());
            //得到装备主键的id传入装备详情当外键
            tbEquipmentDetails.setEquipmentId(tbEquipment.getEquipmentId());
            tbEquipmentDetailsMapper.insert(tbEquipmentDetails);
        }
    }
}
