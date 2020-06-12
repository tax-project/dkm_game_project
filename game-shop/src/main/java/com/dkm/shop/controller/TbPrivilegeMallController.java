package com.dkm.shop.controller;


import com.dkm.jwt.islogin.CheckToken;
import com.dkm.shop.domain.TbPrivilegeMall;
import com.dkm.shop.service.ITbPrivilegeMallService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *游戏项目特权商店
 * @author zy
 * @since 2020-06-09
 */
@RestController
@RequestMapping("/dkm/tbPrivilegeMall")
@Api(description = "游戏项目特权商店的接口文档")
public class TbPrivilegeMallController {

    @Autowired
    ITbPrivilegeMallService tbPrivilegeMallService;

    /**
     * 查询所有的特权商店数据
     * @return
     */
    @ApiOperation(value = "查询所有的特权商店数据",notes = "如果查询成功返回特权商店数据的json,没有则返回空")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "priId",value = "特权商城主键"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "priName",value = "商品名字"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "priContent",value = "商品详情"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "priType",value = "商品购买类型 1为财富卷 2为钻石"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "priMoney",value = "商品价格"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp1",value = "需要财富十级购买 1代表十级"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp2",value = "拓展字段2"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp3",value = "拓展字段3"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "priImg",value = "商品图片"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "prContentOne",value = "小详情")
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @GetMapping("selectAll")
    @CrossOrigin
    @CheckToken
    public List<TbPrivilegeMall> selectAll(){
        List<TbPrivilegeMall> list=tbPrivilegeMallService.selectAll();
        return list;
    }
    /**
     * 增加特权商店的接口
     * @return
     */
    @ApiOperation(value = "增加特权商店的接口",notes = "成功返回成功 失败返回失败")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "priId",value = "特权商城主键"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "priName",value = "商品名字",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "priContent",value = "商品详情",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "priType",value = "商品购买类型 1为财富卷 2为钻石",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "priMoney",value = "商品价格",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp1",value = "需要财富十级购买 1代表十级"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp2",value = "拓展字段2"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp3",value = "拓展字段3"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "priImg",value = "商品图片",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "prContentOne",value = "小详情")
    })
    @PostMapping("addTbPrivilegeMall")
    @CrossOrigin
    @CheckToken
    public void addTbPrivilegeMall(@RequestBody TbPrivilegeMall tbPrivilegeMall){
         tbPrivilegeMallService.insert(tbPrivilegeMall);
    }
}
