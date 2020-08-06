package com.dkm.shopCart.dao;

import com.dkm.shopCart.entities.vo.ShopCartItemInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ShopCartMapper {
    List<ShopCartItemInfo> selectShopCartListById(Long userId);
}
