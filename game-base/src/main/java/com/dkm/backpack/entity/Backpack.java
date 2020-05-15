package com.dkm.backpack.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: HuangJie
 * @Date: 2020/5/14 14:59
 * @Version: 1.0V
 */
@Data
@TableName("tb_backpack")
public class Backpack {
    /**
     * 背包表主键
     */
    @TableId
    private Long backpackId;
    /**
     * 绑定的用户主表外键
     */
    private Long userId;
    /**
     * 背包容量
     */
    private Integer backpackCapacity;
    /**
     * 用户ID
     */
    private Long itemId;
    /**
     * 物品数量
     */
    private Integer itemNumber;
    /**
     * 物品所在表的名称，例如：十五表、物品表等等。
     */
    private String itemTableName;
}
