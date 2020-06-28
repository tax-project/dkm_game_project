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
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "twgId",value = "每周礼包主键"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "twgTitle",value = "商品标题"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "twgImg",value = "商品图片"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "twgContnet",value = "商品描述"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "twgMoney",value = "价格花费钻石"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "twgQuan",value = "商品所得卷"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "twiQid",value = "物品表外键 卷的外键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "twgJinbi",value = "商品所得金币"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "twgTiliId",value = "物品表外键 体力瓶外键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "twgTili",value = "商品所得体力瓶"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "twgJinId",value = "物品表外键 技能外键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "twgJin",value = "商品所得技能道具"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "twgLuckyId",value = "物品表外键 幸运星外键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "twgLucky",value = "商品幸运星"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "twgSpId",value = "物品表外键万能碎片外键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "twgSp",value = "商品万能碎片"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "twgWnsp",value = "商品传奇碎片"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "twgWnspId",value = "物品表外键传奇碎片外键"),
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
