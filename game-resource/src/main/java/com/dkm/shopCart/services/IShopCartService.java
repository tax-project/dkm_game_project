package com.dkm.shopCart.services;

import com.dkm.shopCart.entities.vo.ShopCartItemInfo;
import com.dkm.shopCart.entities.vo.ShopCartItemVo;

import java.util.List;

/**
 * 购物车
 */
public interface IShopCartService {
    /**
     * 根据 userId 来获取购物车信息
     */
    List<ShopCartItemInfo> getShopCartInfo(Long userId);

    Boolean addItems(Long userId, List<ShopCartItemVo> items);

    Boolean updateItems(List<ShopCartItemVo> items);

    Boolean delete(Long id);
}
