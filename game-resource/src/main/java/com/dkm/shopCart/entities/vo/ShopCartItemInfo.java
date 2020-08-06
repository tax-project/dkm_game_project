package com.dkm.shopCart.entities.vo;

import lombok.Data;

/**
 * shop cart Info
 *
 * @version 1.0
 * @date 2020/8/6
 */
@Data
public class ShopCartItemInfo {
    /**
     * 购物车是否被选中
     */
    private Boolean checked = Boolean.FALSE;
    /**
     * 商品的名称
     */
    private String goodsName;
    /**
     * 商品的图片地址
     */
    private String goodsImageUrl;
    /**
     * 商品的数量
     */
    private Integer goodsSize;
    /**
     * 商品的单价
     */
    private Integer goodsPrice;

    /**
     * 商品的总价格
     */
    private Integer totalPrice;
}
