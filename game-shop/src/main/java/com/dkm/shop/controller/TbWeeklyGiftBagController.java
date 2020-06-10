package com.dkm.shop.controller;


import com.dkm.jwt.islogin.CheckToken;
import com.dkm.shop.domain.TbWeeklyGiftBag;
import com.dkm.shop.service.ITbWeeklyGiftBagService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *  每周礼包
 * @author zy
 * @since 2020-06-10
 */
@RestController
@RequestMapping("/dkm/tbWeeklyGiftBag")
@Api(description = "游戏项目每周礼包的接口文档")
public class TbWeeklyGiftBagController {
    @Autowired
    ITbWeeklyGiftBagService tbWeeklyGiftBagService;

    /**
     * 查询每周礼包的数据
     * @return
     */
    @ApiOperation(value = "查询所有每周礼包的数据",notes = "如果查询成功返回每周礼包商据的json,没有则返回空")
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
    public List<TbWeeklyGiftBag> selectAll(){
        List<TbWeeklyGiftBag> list=tbWeeklyGiftBagService.selectAll();
        return list;
    }
	
}
