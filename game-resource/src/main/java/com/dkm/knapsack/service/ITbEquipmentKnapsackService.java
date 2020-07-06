package com.dkm.knapsack.service;

import com.dkm.knapsack.domain.TbEquipment;
import com.dkm.knapsack.domain.TbEquipmentKnapsack;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackTwoVo;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVo;
import com.dkm.knapsack.domain.vo.TbNumberVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2020-05-14
 */
public interface ITbEquipmentKnapsackService{


    List<TbEquipmentKnapsackVo> selectUserId();
    List<TbEquipmentKnapsackVo> selectUserIdTwo(Long userId);
    List<TbEquipmentKnapsackVo> selectProps();
    List<TbEquipmentKnapsackVo> selectPropsTwo(TbEquipmentKnapsackVo tbEquipmentKnapsackVo);
    List<TbEquipmentKnapsackVo> selectFoodId();
    List<TbEquipmentKnapsackVo> selectFoodIdTwo();
    void addTbEquipmentKnapsack(TbEquipmentKnapsack tbEquipmentKnapsack);

    void addTbEquipmentKnapsackTwo(String equipmentId);
    void deleteTbEquipment(Long tekId,Integer tekMoney);
    void addTbPrivilegeMall(TbEquipmentKnapsack tbEquipmentKnapsack);
    Map<String,Object> findById(Long equipmentId);
    int selectCountMy(TbEquipmentKnapsackVo tbEquipmentKnapsackVo);
    List<TbEquipmentKnapsackVo> selectAll(TbEquipmentKnapsackVo tbEquipmentKnapsackVo);
    void updateSell(Long tekId);
    void updateTekId(Long tekId);
    /**
     * 食物和道具试用修改的方法
     */
    void updateIsva(Long tekId,Integer foodNumber);

    List<TbEquipmentKnapsackVo> selectUserIdAndFoodId(Long userId);

    /**
     * 查询当前用户的背包容量
     * @return
     */
    int selectCount();

    /**
     * 用户使用三条鱼兑换一个蜂蜜的接口
     * @param tbNumberVo
     * @return
     */
    int updateFood(TbNumberVo tbNumberVo);

    void addTbEquipmentKnapsackThree(TbEquipmentKnapsack tbEquipmentKnapsack,Long userId);

    void updateAndInsert(TbEquipmentKnapsackTwoVo tbEquipmentKnapsackTwoVo);
}
