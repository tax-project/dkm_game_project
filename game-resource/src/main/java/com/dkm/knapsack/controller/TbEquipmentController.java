package com.dkm.knapsack.controller;



import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;

import com.dkm.knapsack.domain.vo.TbEquipmentVo;
import com.dkm.knapsack.service.ITbEquipmentService;
import com.dkm.knapsack.utils.Message;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp2",value = "装备编号 1-10之间"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp2",value = "道具50金币 穿戴品只有5金币"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp3",value = "1为穿戴品 2为道具"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "edDetailedDescription",value = "详情描述"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "edEquipmentReputation",value = "装备声望"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edRedEnvelopeAcceleration",value = "红包加速"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edDefense",value = "防御/才华"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edAttribute",value = "属性加成 1就代表有加成 0代表没有加成"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edLife",value = "装备生命"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edAttack",value = "攻击力"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edType",value = "1 为生命加成 2为才华加成"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edTypevalue",value = "生命或才华的值"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edTypeone",value = "1为踢出群聊 2为胡言乱语闪避"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edTypeonevalue",value = "踢出群聊和胡言乱语闪避的值")
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
    @CheckToken
    public Message addTbEquipment(@RequestBody TbEquipmentVo tbEquipmentVo){
        Message message=new Message();
        tbEquipmentService.addTbEquipment(tbEquipmentVo);
        message.setMsg("增加成功!");
        message.setNum(1);
        return message;
    }

   @ApiOperation(value = "根据装备主键查询详情信息",notes = "成功返回数据 返回空则没有数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "equipmentId",value = "装备主键",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "edId",value = "装备详情主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "boxId",value = "宝箱主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "equipmentLevel",value = "装备等级"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "equipmentImage",value = "装备图片"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp1",value = "装备编号 1-10之间"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp2",value = "道具50金币 穿戴品只有5金币"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp3",value = "1为穿戴品 2为道具"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "edDetailedDescription",value = "详情描述"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "edEquipmentReputation",value = "装备声望"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edRedEnvelopeAcceleration",value = "红包加速"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edDefense",value = "防御/才华"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edAttribute",value = "属性加成 1就代表有加成 0代表没有加成"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edLife",value = "装备生命"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edAttack",value = "攻击力"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edType",value = "1 为生命加成 2为才华加成"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edTypevalue",value = "生命或才华的值"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edTypeone",value = "1为踢出群聊 2为胡言乱语闪避"),
            @ApiImplicitParam(paramType = "query",dataType = "BigDecimal",name = "edTypeonevalue",value = "踢出群聊和胡言乱语闪避的值")
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @GetMapping("/selectByEquipmentId/{equipmentId}")
    @CrossOrigin
    @CheckToken
    public List<TbEquipmentVo> selectByEquipmentId(@PathVariable("equipmentId") String equipmentId){
        if(!StringUtils.isEmpty(equipmentId)){
            if(StringUtils.isEmpty(equipmentId)){
                throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
            }
        }
       List<TbEquipmentVo> list = tbEquipmentService.selectByEquipmentId(Long.valueOf(equipmentId));
       if(!StringUtils.isEmpty(list)){
           return list;
       }else{
           return null;
       }
    }



    @ApiOperation(value = "批量出售的接口文档",notes = "成功返回成功 失败则返回失败")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "equipmentId",value = "装备主键",required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @GetMapping("/listEquipmentId")
    @CrossOrigin
    @CheckToken
    public void listEquipmentId(@RequestParam("equipmentId") String equipmentId){
        tbEquipmentService.listEquipmentId(equipmentId);
    }
}
