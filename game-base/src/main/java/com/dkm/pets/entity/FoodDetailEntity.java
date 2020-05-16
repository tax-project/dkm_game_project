package com.dkm.pets.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @program: game_project
 * @description: 食物详情表
 * @author: zhd
 * @create: 2020-05-16 18:56
 **/
@Data
@TableName("tb_food_detail")
public class FoodDetailEntity {
    @TableId
    private Long foodId;
    /**
     * 食物名称
     */
    private String foodName;
    /**
     * 食物图片地址
     */
    private String foodUrl;
    /**
     * 食物出售价格
     */
    private Integer foodGold;
}
