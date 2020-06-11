package com.dkm.shop.controller;


import com.dkm.jwt.islogin.CheckToken;
import com.dkm.shop.domain.TbGrowth;
import com.dkm.shop.service.ITbGrowthService;
import com.dkm.shop.utils.Message;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zy
 * @since 2020-06-11
 */
@RestController
@RequestMapping("/dkm/tbGrowth")
@Api(description = "游戏项目成长奖励接口文档")
public class TbGrowthController {
    @Autowired
    ITbGrowthService tbGrowthService;

    @ApiOperation(value = "购买成长奖励或者是立即领取的接口",notes = "如果是立即领取的接口 需要传入 tbIsva=2 和tbNum")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tbIsva",value = "1代表充值了 2代表领取钻石"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tbNum",value = "领取数量"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tbDj",value = "领取等级")
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @PostMapping("insertTbGrowth")
    @CrossOrigin
    @CheckToken
	public void insertTbGrowth(@RequestBody TbGrowth tbGrowth){
        tbGrowthService.insertTbGrowth(tbGrowth);
    }
    @ApiOperation(value = "查询该用户有没有充值",notes = "充值则返回1 没有返回0")
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @GetMapping("selectCountMy")
    @CrossOrigin
    @CheckToken
    public Message selectCountMy(){
        Message message=new Message();
        int rows=tbGrowthService.selectCountMy();
        if(rows>0){
            message.setNum(1);
            message.setMsg("该用户已经充值");
        }else{
            message.setNum(0);
            message.setMsg("该用户没有充值");
        }
        return message;
    }
    @ApiOperation(value = "查询该用户等级奖励领取没有",notes = "领取了则返回1 没有返回0")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "tbDj",value = "领取等级",required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @PostMapping("selectDj")
    @CrossOrigin
    @CheckToken
    public Message selectDj(@RequestBody TbGrowth tbGrowth){
        Message message=new Message();
        int rows=tbGrowthService.selectCountDj(tbGrowth);
        if(rows>0){
            message.setMsg("该用户已经领取");
            message.setNum(1);
        }else{
            message.setNum(0);
            message.setMsg("该用户没有领取");
        }
        return message;
    }
}
