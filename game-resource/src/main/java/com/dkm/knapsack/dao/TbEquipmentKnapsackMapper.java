package com.dkm.knapsack.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.knapsack.domain.TbEquipmentKnapsack;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVo;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author zy
 * @since 2020-05-14
 */
@Service
public interface TbEquipmentKnapsackMapper extends BaseMapper<TbEquipmentKnapsack> {
    List<TbEquipmentKnapsackVo> selectUserId(Long userId);
    List<TbEquipmentKnapsackVo> selectFoodId(Long userId);

    /**
     * 查询道具的接口
     * @param userId
     * @return
     */
    List<TbEquipmentKnapsackVo> selectProps(Long userId);

    List<TbEquipmentKnapsackVo> selectPropsTwo(TbEquipmentKnapsackVo tbEquipmentKnapsackVo);

    int selectCountMy(TbEquipmentKnapsackVo tbEquipmentKnapsackVo);
    int selectCountAll(Long knapsackId);
    List<TbEquipmentKnapsackVo> selectAll(TbEquipmentKnapsackVo tbEquipmentKnapsackVo);
}