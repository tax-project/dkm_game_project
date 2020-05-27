package com.dkm.knapsack.controller;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.knapsack.domain.TbBox;
import com.dkm.knapsack.domain.vo.TbEquipmentVo;
import com.dkm.knapsack.service.ITbBoxService;
import com.dkm.knapsack.utils.Message;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 宝箱表 前端控制器
 * </p>
 *
 * @author zy
 * @since 2020-05-14
 */
@RequestMapping("/dkm/tbBox")
@RestController
@Api(description = "宝箱表的接口文档")
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
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "boxId",value = "宝箱主键"),
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
    @ApiOperation(value = "根据宝箱的主键查询装备",notes = "成功返回成功 无数据则返回空")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "boxId",value = "背包主键",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "boxNo",value = "箱子编号"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "boxType",value = "箱子类型 1为普通箱子 2为VIP箱子"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "equipmentId",value = "装备主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "edId",value = "装备详情主键"),
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
    @GetMapping("/selectByBoxId")
    @CrossOrigin
    @CheckToken
    public TbEquipmentVo selectByBoxId(String boxId){
        TbEquipmentVo list=tbBoxService.selectByBoxId(boxId);
        if(!StringUtils.isEmpty(list)){
            return list;
        }else{
            return null;
        }
    }

    @ApiOperation(value = "批量宝箱的主键查询装备 要传json",notes = "成功返回成功 无数据则返回空")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "boxId",value = "背包主键",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "boxNo",value = "箱子编号"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "boxType",value = "箱子类型 1为普通箱子 2为VIP箱子"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "equipmentId",value = "装备主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "edId",value = "装备详情主键"),
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
    @PostMapping("/selectByBoxIdTwo")
    @CrossOrigin
    @CheckToken
    public List<TbEquipmentVo> selectByBoxIdTwo(@RequestBody List<Long> boxId){
          return tbBoxService.selectByBoxIdTwo(boxId);
    }

    /**
     * 查询所有宝箱的方法
     * @return
     */
    @ApiOperation(value = "查询所有宝箱的方法",notes = "成功返回数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "boxId",value = "宝箱主键"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "boxNo",value = "箱子编号",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "boxType",value = "箱子类型 1为普通箱子 2为VIP箱子",required = true)
    })
    @GetMapping("/selectAll")
    @CrossOrigin
    @CheckToken
    public List<TbBox> selectAll(){
        List<TbBox> list=tbBoxService.selectAll();
        if(!StringUtils.isEmpty(list)){
            return list;
        }else{
            return null;
        }
    }
}
