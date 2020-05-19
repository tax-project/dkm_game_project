package com.dkm.knapsack.controller;


import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.knapsack.domain.TbKnapsack;
import com.dkm.knapsack.service.ITbKnapsackService;
import com.dkm.knapsack.utils.Message;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zy
 * @since 2020-05-14
 */
@RestController
@RequestMapping("/dkm/tbKnapsack")
@Api(description = "背包表的接口文档")
public class TbKnapsackController {
    @Autowired
    ITbKnapsackService tbKnapsackService;

    @Autowired
    private LocalUser localUser;
    /**
     * 给用户分配背包的增加接口
     * @param tbKnapsack
     * @return
     */
    @ApiOperation(value = "用户分配背包的增加接口",notes = "成功返回成功")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "knapsackId",value = "背包主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "userId",value = "用户id"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "knapsackCapacity",value = "背包容量 默认 30 VIP容纳60")
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @PostMapping("/addTbKnapsack")
    @CrossOrigin
    @CheckToken
    public Message addTbKnapsack(@RequestBody TbKnapsack tbKnapsack){
        UserLoginQuery user = localUser.getUser();
        tbKnapsack.setUserId(user.getId());
        Message message=new Message();
        tbKnapsackService.addTbKnapsack(tbKnapsack);
        message.setMsg("增加成功");
        message.setNum(1);
        return message;
    }

    /**
     * 根据用户id查询背包容量
     * @param tbKnapsack
     * @return
     */
    @ApiOperation(value = "根据用户id查询背包容量",notes = "成功返回成功")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "knapsackId",value = "背包主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "userId",value = "用户id"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "knapsackCapacity",value = "背包容量 默认 30 VIP容纳60")
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @PostMapping("/findById")
    @CrossOrigin
    @CheckToken
    public List<TbKnapsack> findById(TbKnapsack tbKnapsack){
        UserLoginQuery user = localUser.getUser();
        tbKnapsack.setUserId(user.getId());
        List<TbKnapsack> list=tbKnapsackService.findById(tbKnapsack);
        return list;
    }

    /**
     * 根据当前登录人成vip后修改背包容量
     * @param tbKnapsack
     */
    @ApiOperation(value = "根据当前登录人成vip后修改背包容量",notes = "成功返回成功")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "knapsackId",value = "背包主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "userId",value = "用户id"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "knapsackCapacity",value = "背包容量 默认 30 VIP容纳60")
    })
    @GetMapping("/updateByPrimaryKeySelective")
    @CrossOrigin
    @CheckToken
    public void updateByPrimaryKeySelective(TbKnapsack tbKnapsack){
        tbKnapsackService.updateByPrimaryKeySelective(tbKnapsack);
    }
}
