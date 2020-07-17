package com.dkm.equipment.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.equipment.entity.EquipmentEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 装备详情
 * @author: zhd
 * @create: 2020-07-17 20:56
 **/
@Mapper
public interface EquipmentMapper extends IBaseMapper<EquipmentEntity> {
}
