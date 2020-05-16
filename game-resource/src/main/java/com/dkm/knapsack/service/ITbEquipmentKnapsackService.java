package com.dkm.knapsack.service;

import com.dkm.knapsack.domain.TbEquipment;
import com.dkm.knapsack.domain.TbEquipmentKnapsack;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVo;

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
    void addTbEquipmentKnapsack(TbEquipmentKnapsack tbEquipmentKnapsack);
    void deleteTbEquipment(Long tekId,Integer tekMoney);
    Map<String,Object> findById(Long equipmentId);
    int selectCountMy(String exp1);
    List<TbEquipmentKnapsackVo> selectAll(String exp1);
    void updateSell(Long tekId);
    void uodateTekId(Long tekId);
}
