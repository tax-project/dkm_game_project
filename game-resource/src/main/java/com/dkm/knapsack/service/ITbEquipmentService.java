package com.dkm.knapsack.service;


import com.dkm.knapsack.domain.vo.TbEquipmentVo;

import java.util.List;

/**
 * <p>
 * 装备表 服务类
 * </p>
 *
 * @author zy
 * @since 2020-05-14
 */
public interface ITbEquipmentService{
	void addTbEquipment(TbEquipmentVo tbEquipmentVo);

	void listEquipmentId(String equipmentId);

	List<TbEquipmentVo> selectByEquipmentId(Long equipmentId);

	TbEquipmentVo selectByEquipmentIdTwo(String exp1);

	void listEquipmentIdTwo(String equipmentId);
}
