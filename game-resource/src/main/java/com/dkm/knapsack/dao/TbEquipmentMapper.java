package com.dkm.knapsack.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.knapsack.domain.TbEquipment;
import com.dkm.knapsack.domain.vo.TbEquipmentVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
  * 装备表 Mapper 接口
 * </p>
 *
 * @author zy
 * @since 2020-05-14
 */
@Service
public interface TbEquipmentMapper extends BaseMapper<TbEquipment> {
    List<TbEquipmentVo> selectByEquipmentId(Long equipmentId);

    TbEquipmentVo selectByEquipmentIdTwo(String exp1);
}