package com.dkm.shopCart.services.impl;

import com.dkm.shopCart.dao.ShopCartMapper;
import com.dkm.shopCart.entities.vo.ShopCartItemInfo;
import com.dkm.shopCart.services.IShopCartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ShopCartServiceImpl implements IShopCartService {
    @Resource
    private ShopCartMapper shopCartMapper;
    @Override
    public List<ShopCartItemInfo> getShopCartInfo(Long userId) {
        return shopCartMapper.selectShopCartListById(userId);
    }
}
