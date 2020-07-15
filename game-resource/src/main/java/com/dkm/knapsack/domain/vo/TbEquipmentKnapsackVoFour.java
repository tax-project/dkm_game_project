package com.dkm.knapsack.domain.vo;



import lombok.Data;

/**
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @USER: 周佳佳
 * @DATE: 2020/5/15 11:00
 */
@Data
public class TbEquipmentKnapsackVoFour {


    /**
     * 装备背包表主键
     */
    private Long tekId;

    /**
     * 道具拥有数量
     */
    private Integer foodNumber;
    /**
     * 体力加成
     */
    private String goodContent;

    /**
     * 体力瓶名字
     */
    private String name;
    /**
     * 体力瓶图片地址
     */
    private String url;
}
