package com.dkm.shop.controller;


import com.dkm.jwt.islogin.CheckToken;
import com.dkm.shop.domain.TbThedailyOne;
import com.dkm.shop.service.ITbThedailyOneService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器</p>
 *  每日礼包充值记录表
 * @author zy
 * @since 2020-06-21
 */
@RestController
@RequestMapping("/dkm/tbThedailyOne")
@Api(description = "游戏项目每日礼包充值记录的接口文档")
public class TbThedailyOneController {
    @Autowired
    ITbThedailyOneService tbThedailyOneService;
    /**
     * 查询每日礼包是否被买过
     * @return
     */
    @ApiOperation(value = "查询每日礼包是否被买过",notes = "返回2代表今天已经买过了 返回0代表没有")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "thdId",value = "每日礼包外键",required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @GetMapping("/selectIva")
    @CrossOrigin
    @CheckToken
    public int selectIva(@RequestParam("thdId") String thdId){
        return tbThedailyOneService.selectCountThd(thdId);
    }

    @ApiOperation(value = "增加每日礼包充值记录",notes = "成功返回成功 失败返回失败")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "thdId",value = "每日礼包外键",required = true),
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
    public void insert(@RequestParam("thdId") String thdId){
       tbThedailyOneService.insert(thdId);
    }
}
