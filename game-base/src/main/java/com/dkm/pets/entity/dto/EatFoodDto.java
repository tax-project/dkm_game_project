package com.dkm.pets.entity.dto;

import lombok.Data;

/**
 * @author zhd
 * @date 2020/5/10 11:26
 */
@Data
public class EatFoodDto {

    /**
     * 食物图片
     */
    private String foodUrl;

    /**
     * 食物数量
     */
    private Integer eNumber;
    /**
     * 食物数量
     */
    private Integer  foodNumber;

    /**
     * 食物名称
     * */
    private String foodName;
    /**
     * 食物id
     */
    private Long foodId;
}
