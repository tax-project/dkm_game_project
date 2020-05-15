package com.dkm.knapsack.controller;


import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.knapsack.domain.TbBox;
import com.dkm.knapsack.service.ITbBoxService;
import com.dkm.knapsack.utils.Message;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 宝箱表 前端控制器
 * </p>
 *
 * @author zy
 * @since 2020-05-14
 */
@Controller
@RequestMapping("/dkm/tbBox")
public class TbBoxController {
	@Autowired
    ITbBoxService tbBoxService;

    /**
     * 添加宝箱的方法
     * @param tbBox
     * @return
     */
    @ApiOperation(value = "添加宝箱的增加接口",notes = "成功返回成功")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "boxId",value = "背包主键"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "boxNo",value = "箱子编号",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "boxType",value = "箱子类型 1为普通箱子 2为VIP箱子",required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @PostMapping("/addTbBox")
    @CrossOrigin
    @CheckToken
    public Message addTbBox(@RequestBody TbBox tbBox){
        Message message=new Message();
        tbBoxService.addTbBox(tbBox);
        message.setMsg("增加成功");
        message.setNum(1);
        return message;
    }
}
