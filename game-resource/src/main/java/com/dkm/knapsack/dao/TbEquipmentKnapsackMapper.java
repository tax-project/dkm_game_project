package com.dkm.knapsack.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.knapsack.domain.TbEquipmentKnapsack;
import com.dkm.knapsack.domain.vo.*;
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
    List<TbEquipmentKnapsackVoCenter> selectUserIdTwo(Long userId);

    List<TbEquipmentKnapsackVo> selectFoodId(Long userId);
    List<TbEquipmentKnapsackVo> selectFoodIdTwo(Long userId);

    /**
     * 返回金星星数量给梦琪
     * @param knapsackId
     * @return
     */
    TbEquipmentKnapsackVoThree selectNumberStar(Long knapsackId);

    /**
     * 查询个人中心的体力瓶
     * @param knapsackId 背包id
     * @return
     */
    List<TbEquipmentKnapsackVoFour> selectPersonCenter(Long knapsackId);
    /**
     * 查询道具的接口
     * @param userId
     * @return
     */
    List<TbEquipmentKnapsackVo> selectProps(Long userId);
    List<TbEquipmentKnapsackVo> selectPropsTwo(TbEquipmentKnapsackVo tbEquipmentKnapsackVo);

    int selectCountMy(TbEquipmentKnapsackVo tbEquipmentKnapsackVo);
    int selectCountAll(Long knapsackId);
    List<TbEquipmentKnapsackVo> selectUserIdThree(Long userId);
    List<TbEquipmentKnapsackVo> selectAll(TbEquipmentKnapsackVo tbEquipmentKnapsackVo);
}