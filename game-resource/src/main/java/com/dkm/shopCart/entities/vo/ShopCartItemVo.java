package com.dkm.shopCart.entities.vo;

import lombok.Data;

/**
 *
 *
 * @date 2020/8/7
 */
@Data
public class ShopCartItemVo {
    private Long itemId;
    /**
     * 物品 ID
     */
    private Long goodsId;
    private Integer size;
}
