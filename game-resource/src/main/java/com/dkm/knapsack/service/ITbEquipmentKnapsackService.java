package com.dkm.knapsack.service;

import com.dkm.knapsack.domain.TbEquipment;
import com.dkm.knapsack.domain.TbEquipmentKnapsack;
import com.dkm.knapsack.domain.vo.*;

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
    List<TbEquipmentKnapsack> findByIdAndId(Long tekId);

    List<TbEquipmentKnapsackVo> selectUserId();
    List<TbEquipmentKnapsackVo> selectUserIdTwo(Long userId);
    List<TbEquipmentKnapsackVoCenter> selectUserIdThree(Long userId);
    List<TbEquipmentKnapsackVo> selectProps();
    List<TbEquipmentKnapsackVo> selectPropsTwo(TbEquipmentKnapsackVo tbEquipmentKnapsackVo);
    List<TbEquipmentKnapsackVo> selectFoodId();
    List<TbEquipmentKnapsackVo> selectFoodIdTwo();
    void addTbEquipmentKnapsack(TbEquipmentKnapsack tbEquipmentKnapsack);
    List<TbEquipmentKnapsackVo> selectUserIdFour();
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
    /**
     * 体力瓶的修改方法 并且加体力
     */
    void updateIsvaTwo(Long tekId,Integer foodNumber,String goodContent);

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

    /**
     * 返回金星星数量给梦琪
     * @return
     */
    TbEquipmentKnapsackVoThree selectNumberStar();

    /**
     * 个人中心返回体力瓶数量
     * @return
     */
    List<TbEquipmentKnapsackVoFour> selectPersonCenter(Long userId);
}
