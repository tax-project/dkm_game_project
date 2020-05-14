package com.dkm.pets.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zhd
 * @date 2020/5/9 9:12
 */
@Data
@TableName("tb_food")
public class FoodEntity {
    @TableId
    private Long id;

    /**
     * 用户id
     * */
    private Long userId;

    /**
     * 食物id
     * */
    private Long foodId;

    /**
     * 食物数量
     * */
    private Integer foodNumber;

    /*
    * 背包主键
    */
    private Long knapsackId;

}
