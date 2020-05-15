package com.dkm.knapsack.service;


import com.dkm.knapsack.domain.vo.TbEquipmentVo;

import java.util.List;

/**
 * <p>
 * 装备表 服务类
 * </p>
 *
 * @author zy
 * @since 2020-05-12
 */
public interface ITbEquipmentServiceTwo {
    List<TbEquipmentVo> selectByKnapsackId(TbEquipmentVo tbEquipmentVo);
    void updateExp1(Long equipmentId);
}
