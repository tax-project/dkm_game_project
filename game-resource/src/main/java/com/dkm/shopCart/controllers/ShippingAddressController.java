package com.dkm.shopCart.controllers;


import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.shopCart.entities.vo.ShippingAddressInfoVo;
import com.dkm.shopCart.entities.vo.ShippingAddressVo;
import com.dkm.shopCart.services.IShippingAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = {"购物车api", "收货地址API"})
@RestController
@RequestMapping("/shop/address/")
public class ShippingAddressController {
    @Resource
    private LocalUser localUser;

    @Resource
    private IShippingAddressService addressService;

    @ApiOperation("查看个人的全部收货地址")
    @CrossOrigin
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @CheckToken
    @GetMapping(value = "/getAddress", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ShippingAddressInfoVo> getShopCartInfo() {
        Long userId = localUser.getUser().getId();
        return addressService.getAllAddress(userId);
    }


    @ApiOperation("添加一条收货地址信息")
    @CrossOrigin
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @CheckToken
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean addAShippingAddress(@RequestBody ShippingAddressVo shippingAddressVo) {
        Long userId = localUser.getUser().getId();
        return addressService.addAShippingAddress(userId, shippingAddressVo);
    }

    @ApiOperation("修改一条收货地址信息")
    @CrossOrigin
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token"),
            @ApiImplicitParam(paramType = "path", name = "id", required = true, dataType = "Long", value = "修改的ID")

    })
    @CheckToken
    @PostMapping(value = "/{id}/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean updateAShippingAddressById(@RequestBody ShippingAddressVo shippingAddressVo, @PathVariable("id") Long itemId) {
        Long userId = localUser.getUser().getId();
        return addressService.updateAShippingAddressById(userId, itemId, shippingAddressVo);
    }


    @ApiOperation("删除收货地址信息")
    @CrossOrigin
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token"),
            @ApiImplicitParam(paramType = "path", name = "id", required = true, dataType = "Long", value = "要删除的ID")

    })
    @CheckToken
    @GetMapping(value = "/{id}/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean delete( @PathVariable("id") Long itemId) {
        Long userId = localUser.getUser().getId();
        return addressService.delete(userId, itemId);
    }


}
