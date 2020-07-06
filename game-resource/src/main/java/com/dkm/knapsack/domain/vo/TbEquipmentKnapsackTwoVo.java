package com.dkm.knapsack.domain.vo;



import lombok.Data;

import java.math.BigDecimal;

/**
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @USER: 周佳佳
 * @DATE: 2020/5/15 11:00
 */
@Data
public class TbEquipmentKnapsackTwoVo {

    /**
     * 装备背包表主键
     */
    public Long tekId;
    /**
     * 装备主键
     */
    public Long equipmentId;

    /**
     * 是否有效
     */
    public Integer tekIsva;
}
