package com.dkm.pets.entity.vo;



import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @USER: 周佳佳
 * @DATE: 2020/5/15 11:00
 */
@Data
public class TbEquipmentKnapsackVo {

    /**
     * 装备背包表主键
     */
    private Long tekId;

    /**
     * 食物主键
     */
    private Long foodId;
    /**
     * 食物数量
     */
    private Integer foodNumber;

    /**
     * 食物名字
     */
    private String foodName;
    /**
     * 食物图片地址
     */
    private String foodUrl;

}
