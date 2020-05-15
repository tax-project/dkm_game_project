package com.dkm.backpack.entity.vo;

import lombok.Data;

/**
 * @Author: HuangJie
 * @Date: 2020/5/15 11:32
 * @Version: 1.0V
 */
@Data
public class BackpackItemVO {
    /**
     * 物品ID
     */
    private Long itemId;
    /**
     * 物品名称
     */
    private String itemName;
    /**
     * 物品数量
     */
    private Integer itemNumber;
    /**
     * 物品头像地址
     */
    private String itemImageUrl;
    /**
     * 物品对应的表编号
     */
    private Integer itemTableName;
}
