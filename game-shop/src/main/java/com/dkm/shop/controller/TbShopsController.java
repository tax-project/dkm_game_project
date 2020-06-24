package com.dkm.shop.controller;



import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.shop.domain.TbShops;
import com.dkm.shop.service.TbShopsService;
import com.dkm.shop.utils.Message;
import io.swagger.annotations.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author zy
 * @since 2020-05-09
 */
@RequestMapping("/dkm/tbShops")
@RestController
@Api(description = "游戏项目热销商品的接口文档")
public class TbShopsController {
	@Resource
    TbShopsService tbShopsService;
    /**
     * 查询所有的每日特惠
     * @param
     */
    @ApiOperation(value = "查询所有的热销商品",notes = "如果查询成功返回热销商品的json,没有则返回空")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "shopId",value = "商品主键"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "shopName",value = "商品名称"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "shopMoney",value = "商品价格"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "tsId",value = "物品外键"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp2",value = "拓展字段2"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp3",value = "拓展字段3"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "shopImage",value = "商品图片"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "shopInfo",value = "商品描述")
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @GetMapping("/selectAll")
    @CrossOrigin
    @CheckToken
    public List<TbShops> selectAll(){
        List<TbShops> list=tbShopsService.selectAll();
        return list;
    }

    /**
     * 增加热销商品的接口
     * @param tbDayCheap
     */
    @ApiOperation(value = "增加热销商品的接口",notes = "成功则返回操作成功")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "shopId",value = "商品主键"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "shopName",value = "商品名称",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "shopMoney",value = "商品价格",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "tsId",value = "物品外键"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp 2",value = "拓展字段2"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp3",value = "拓展字段3"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "shopImage",value = "商品图片",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "shopInfo",value = "商品描述",required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @PostMapping("/addTbDayCheap")
    @ResponseBody
    @CrossOrigin
    @CheckToken
    public Message addTbDayCheap(@RequestBody TbShops tbDayCheap){
        Message errorResult=new Message();
        if(StringUtils.isEmpty(tbDayCheap.getShopName()) || StringUtils.isEmpty(tbDayCheap.getShopMoney())
                || StringUtils.isEmpty(tbDayCheap.getShopImage()) || StringUtils.isEmpty(tbDayCheap.getShopInfo())){
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "必填参数不能为空");
        }
        tbShopsService.addTbShops(tbDayCheap);
        errorResult.setNum(1);
        errorResult.setMsg("操作成功");
        return errorResult;
    }
}
