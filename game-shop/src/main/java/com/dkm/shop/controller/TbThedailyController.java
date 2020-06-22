package com.dkm.shop.controller;


import com.dkm.jwt.islogin.CheckToken;
import com.dkm.shop.domain.TbThedaily;
import com.dkm.shop.service.ITbThedailyService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *  每日礼包
 * @author zy
 * @since 2020-06-21
 */
@RestController
@Api(description = "游戏项目每日礼包的接口文档")
@RequestMapping("/dkm/tbThedaily")
public class TbThedailyController {
    @Autowired
    ITbThedailyService tbThedailyService;
    /**
     * 查询每日礼包的数据
     * @return
     */
    @ApiOperation(value = "查询所有每日礼包的数据",notes = "如果查询成功返回每日礼包商据的json,没有则返回空")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "thdId",value = "每日礼包的主键"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "thdName",value = "每日礼包名字"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "thdDiscount",value = "打折字段"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "thdImg",value = "商品图片"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "thdMoney",value = "需要花费钻石"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "thdContent",value = "商品详情"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "thdContenttwo",value = "商品详情2"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "thdIsva",value = "1为一天买一次 2为一天二十次"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp1",value = "今日还剩多少次"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp2",value = "拓展字段2"),

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
    public List<TbThedaily> selectAll(){
        List<TbThedaily> list=tbThedailyService.selectAll();
        return list;
    }

}
