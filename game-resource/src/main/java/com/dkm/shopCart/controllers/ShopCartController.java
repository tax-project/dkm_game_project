package com.dkm.shopCart.controllers;

import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.shopCart.entities.vo.ShopCartItemInfo;
import com.dkm.shopCart.entities.vo.ShopCartItemVo;
import com.dkm.shopCart.services.IShopCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import lombok.val;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    @ApiOperation("查询个人购物车里的信息")
    @CrossOrigin
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @CheckToken
    @GetMapping(value = "/getShopCartItems", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShopCartItemInfo> getShopCartInfo() {
        Long userId = localUser.getUser().getId();
        return shopCartService.getShopCartInfo(userId);
    }


    @ApiOperation("添加一条购物车消息")
    @CrossOrigin
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @CheckToken
    @PostMapping(value = "/addItem", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean addItem(@RequestBody ShopCartItemVo item) {
        return addItems(Collections.singletonList(item));
    }

    @ApiOperation("添加大量购物车消息")
    @CrossOrigin
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @CheckToken
    @PostMapping(value = "/addItems", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean addItems(@RequestBody List<ShopCartItemVo> items) {
        return shopCartService.addItems(localUser.getUser().getId(), items);
    }

    @ApiOperation("更新一条购物车信息")
    @CrossOrigin
    @ApiImplicitParams(
            {@ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token"),
                    @ApiImplicitParam(paramType = "path", name = "id", required = true, dataType = "Long", value = "需要更新的购物车ID")
            }
    )
    @CheckToken
    @PostMapping(value = "/{id}/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean updateItem(@RequestBody ShopCartItemVo item, @PathVariable Long id) {
        item.setItemId(id);
        return updateItems(Collections.singletonList(item));
    }

    @ApiOperation("更新大量的购物车信息")
    @CrossOrigin
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @CheckToken
    @PostMapping(value = "/updateItems", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean updateItems(@RequestBody List<ShopCartItemVo> items) {
        return shopCartService.updateItems(items);
    }

    @ApiOperation("删除一条购物车信息")
    @CrossOrigin
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @CheckToken
    @PostMapping(value = "/{id}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean deleteItem(@PathVariable Long id) {
        return shopCartService.delete(id);
    }



}
