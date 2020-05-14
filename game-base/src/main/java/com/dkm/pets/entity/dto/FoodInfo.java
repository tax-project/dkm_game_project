package com.dkm.pets.entity.dto;

import com.dkm.pets.entity.FoodEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhd
 * @date 2020/5/9 16:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FoodInfo extends FoodEntity {
    /**
     * 食物名称
     * */
    private String foodName;
    /**
     * 食物图片地址
     * */
    private String foodUrl;
}
