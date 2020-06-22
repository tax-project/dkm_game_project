package com.dkm.shop.controller;


import com.dkm.jwt.islogin.CheckToken;
import com.dkm.shop.service.ITbAccumulatedOneService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zy
 * @since 2020-06-21
 */
@RestController
@RequestMapping("/dkm/tbAccumulatedOne")
@Api(description = "游戏项目累计积累奖励领取记录接口文档")
public class TbAccumulatedOneController {
    @Autowired
    ITbAccumulatedOneService tbAccumulatedOneService;
    @ApiOperation(value = "增加累计积累奖励领取记录",notes = "成功返回成功 失败返回失败")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "talId",value = "累计充值外键",required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @GetMapping("/insert")
    @CrossOrigin
    @CheckToken
    public void insert(@RequestParam("talId") String talId){
        tbAccumulatedOneService.insert(talId);
    }
    @ApiOperation(value = "查询这个奖励有没有领取过",notes = "领取过返回1 没有返回0")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "talId",value = "累计充值外键",required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @GetMapping("/selectCount")
    @CrossOrigin
    @CheckToken
    public int selectCount(@RequestParam("talId") String talId){
        return tbAccumulatedOneService.selectCount(talId);
    }
}
