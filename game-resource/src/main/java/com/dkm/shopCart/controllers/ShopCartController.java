package com.dkm.shopCart.controllers;

import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.shopCart.entities.vo.ShopCartItemInfo;
import com.dkm.shopCart.services.IShopCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 关于购物车
 * <p>
 * 购物车分为添加商品、删除商品、修改商品数目、删除商品、结算全部商品、结算部分商品
 */
@Api(tags = "购物车api")
@RestController
@RequestMapping("/shop/cart/")
public class ShopCartController {
    @Resource
    private LocalUser localUser;


    @Resource
    private IShopCartService shopCartService;

    @CrossOrigin
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @CheckToken
    @GetMapping(value = "/getShopCartItems", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShopCartItemInfo> getShopCartInfo() {
        Long userId = localUser.getUser().getId();
        return shopCartService.getShopCartInfo(userId);
    }
}
