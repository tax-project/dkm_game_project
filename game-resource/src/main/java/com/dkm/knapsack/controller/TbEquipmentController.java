package com.dkm.knapsack.controller;


import com.dkm.jwt.islogin.CheckToken;
import com.dkm.knapsack.domain.vo.TbEquipmentVo;
import com.dkm.knapsack.service.ITbEquipmentService;
import com.dkm.knapsack.utils.Message;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 装备表 前端控制器
 * </p>
 *
 * @author zy
 * @since 2020-05-14
 */
@RestController
@RequestMapping("/dkm/tbKnapsack")
@ResponseBody
@Api(description = "装备表的接口文档")
public class TbEquipmentController {
    @Autowired
    ITbEquipmentService tbEquipmentService;
    /**
     * 装备表和装备详情的增加方法
     * @param tbEquipmentVo
     * @return
     */
    @ApiOperation(value = "装备和装备详情的增加接口  所有下面input显示的参数全要传",notes = "成功返回成功")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "boxId",value = "宝箱主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "equipmentLevel",value = "装备等级"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "equipmentImage",value = "装备图片"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp2",value = "道具50金币 穿戴品只有5金币"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp3",value = "1为穿戴品 2为道具"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "edDetailedDescription",value = "详情描述"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "edEquipmentReputation",value = "装备声望"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edRedEnvelopeAcceleration",value = "红包加速")
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @PostMapping("/addTbEquipment")
    @CrossOrigin
    //@CheckToken
    public Message addTbEquipment(@RequestBody TbEquipmentVo tbEquipmentVo){
        Message message=new Message();
        tbEquipmentService.addTbEquipment(tbEquipmentVo);
        message.setMsg("增加成功!");
        message.setNum(1);
        return message;
    }
}
