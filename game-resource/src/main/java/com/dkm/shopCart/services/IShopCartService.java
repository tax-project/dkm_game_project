package com.dkm.shopCart.services;

import com.dkm.shopCart.entities.vo.ShopCartItemInfo;

import java.util.List;

/**
 * 购物车
 */
public interface IShopCartService {
    /**
     * 根据 userId 来获取购物车信息
     */
    List<ShopCartItemInfo> getShopCartInfo(Long userId);
}
