package com.dkm.shop.controller;


import com.dkm.jwt.islogin.CheckToken;
import com.dkm.shop.domain.TbAccumulated;
import com.dkm.shop.service.ITbAccumulatedService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zy
 * @since 2020-06-21
 */
@RestController
@RequestMapping("/dkm/tbAccumulated")
@Api(description = "游戏项目累计积累奖励接口文档")
public class TbAccumulatedController {
    @Autowired
    ITbAccumulatedService tbAccumulatedService;
    /**
     * 查询累计积累奖励的数据
     * @return
     */
    @ApiOperation(value = "查询所有累计积累奖励的数据",notes = "如果查询成功返回累计积累奖励的json,没有则返回空")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "talId",value = "累计充值主键"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "talTitle",value = "标题"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "talJb",value = "赠送的金币"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "talJbimg",value = "金币图片"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "talXx",value = "星星数量"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "talXximg",value = "星星图片"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "talHz",value = "徽章数量"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "talHzimg",value = "徽章图片"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp1",value = "拓展字段1"),
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
   /* @CheckToken*/
    public List<TbAccumulated> selectAll(){
        List<TbAccumulated> list=tbAccumulatedService.selectAll();
        return list;
    }
}
