package com.dkm.knapsack.domain.vo;



import lombok.Data;

/**
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @USER: 周佳佳
 * @DATE: 2020/5/15 11:00
 */
@Data
public class TbEquipmentKnapsackVoFive {


    /**
     * 装备背包表主键
     */
    public Long tekId;

    /**
     * 道具拥有数量
     */
    public Integer foodNumber;
    /**
     * 体力加成
     */
    public String goodContent;

}
