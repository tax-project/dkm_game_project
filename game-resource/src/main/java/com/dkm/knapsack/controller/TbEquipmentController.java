package com.dkm.knapsack.controller;


import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.knapsack.domain.TbEquipment;
import com.dkm.knapsack.domain.TbKnapsack;
import com.dkm.knapsack.domain.vo.TbEquipmentVo;
import com.dkm.knapsack.service.ITbEquipmentService;
import com.dkm.knapsack.service.ITbKnapsackService;
import com.dkm.knapsack.utils.Message;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 装备表 前端控制器
 * </p>
 *
 * @author zy
 * @since 2020-05-12
 */
@RestController
@RequestMapping("/dkm/tbEquipment")
@Api(description = "游戏项目装备表的接口文档")
public class TbEquipmentController {
    @Autowired
    ITbEquipmentService tbEquipmentService;
    @Autowired
    ITbKnapsackService tbKnapsackService;
    @Autowired
    private LocalUser localUser;
    /**
     * 根据背包id查询已经装备上的装备
     * @return
     */
    @ApiOperation(value = "根据背包id查询已经装备上的装备的接口",notes = "成功返回数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "equipmentId",value = "装备主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "knapsackId",value = "背包主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "boxId",value = "宝箱主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "equipmentLevel",value = "装备等级"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "equipmentImage",value = "装备图片"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp1",value = "我的装备 1为装备上了 2为背包装备"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp2",value = "道具50金币 穿戴品只有5金币"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp3",value = "1为穿戴品 2为道具"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "edId",value = "装备详情主键"),
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
    @GetMapping("/selectByKnapsackId")
    @CrossOrigin
    @CheckToken
    public List<TbEquipmentVo> selectByKnapsackId(){
        UserLoginQuery user = localUser.getUser();
        TbKnapsack tbKnapsack=new TbKnapsack();
        tbKnapsack.setUserId(user.getId());
        //根据登录的用户id查询主键出背包主键
        List<TbKnapsack> listOne=tbKnapsackService.findById(tbKnapsack);
        List<TbEquipmentVo> list=null;
        TbEquipmentVo tbEquipment=new TbEquipmentVo();
        //判断用户背包添加过
        if(!StringUtils.isEmpty(listOne)){
            for (TbKnapsack knapsack : listOne) {
                tbEquipment.setKnapsackId(knapsack.getKnapsackId());
                tbEquipment.setExp1("1");
                list=tbEquipmentService.selectByKnapsackId( tbEquipment);
            }
            if(!StringUtils.isEmpty(list)){
                return list;
            }else{
                return null;
            }
        }else{
            return null;
        }

    }
    /**
     * 根据背包id查询所有的背包没有装备的装备
     * @return
     */
    @ApiOperation(value = "根据背包id查询所有的背包没有装备的装备",notes = "成功返回数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "equipmentId",value = "装备主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "knapsackId",value = "背包主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "boxId",value = "宝箱主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "equipmentLevel",value = "装备等级"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "equipmentImage",value = "装备图片"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp1",value = "我的装备 1为装备上了 2为背包装备"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp2",value = "道具50金币 穿戴品只有5金币"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp3",value = "1为穿戴品 2为道具"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "edId",value = "装备详情主键"),
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
    @GetMapping("/selectByKnapsackIdTwo")
    @CrossOrigin
    @CheckToken
    public List<TbEquipmentVo> selectByKnapsackIdTwo(){
        UserLoginQuery user = localUser.getUser();
        TbKnapsack tbKnapsack=new TbKnapsack();
        List<TbEquipmentVo> list=null;
        tbKnapsack.setUserId(user.getId());
        //根据登录的用户id查询主键出背包主键
        List<TbKnapsack> listOne=tbKnapsackService.findById(tbKnapsack);
        TbEquipmentVo tbEquipment=new TbEquipmentVo();
        //判断用户背包添加过
        if(!StringUtils.isEmpty(listOne)){
            for (TbKnapsack knapsack : listOne) {
                tbEquipment.setKnapsackId(knapsack.getKnapsackId());
                tbEquipment.setExp1("2");
                list=tbEquipmentService.selectByKnapsackId(tbEquipment);
            }
            if(!StringUtils.isEmpty(list)){
                return list;
            }else{
                return null;
            }
        }else{
            return null;
        }

    }
    /**
     * 根据背包id查询已经装备上的装备是否超过十件
     * @return
     */
    @ApiOperation(value = "根据背包id查询已经装备上的装备的接口",notes = "成功返回0代表用户没有添加背包    1则代表已经装备的背包超过十件不能装备了  2代表还没超过十件可以装")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "equipmentId",value = "装备主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "knapsackId",value = "背包主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "boxId",value = "宝箱主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "equipmentLevel",value = "装备等级"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "equipmentImage",value = "装备图片"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp1",value = "我的装备 1为装备上了 2为背包装备"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp2",value = "道具50金币 穿戴品只有5金币"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp3",value = "1为穿戴品 2为道具")
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @GetMapping("selectCountByKnapsackId")
    @CrossOrigin
    @CheckToken
    public Message selectCountByKnapsackId(){
        Message message=new Message();
        UserLoginQuery user = localUser.getUser();
        TbKnapsack tbKnapsack=new TbKnapsack();
        tbKnapsack.setUserId(user.getId());
        //根据登录的用户id查询主键出背包主键
        List<TbEquipmentVo> list=null;
        List<TbKnapsack> listOne=tbKnapsackService.findById(tbKnapsack);
        TbEquipmentVo tbEquipment=new TbEquipmentVo();
        //判断用户背包添加过
        if(!StringUtils.isEmpty(listOne)){
            for (TbKnapsack knapsack : listOne) {
                tbEquipment.setKnapsackId(knapsack.getKnapsackId());
                tbEquipment.setExp1("2");
                list=tbEquipmentService.selectByKnapsackId(tbEquipment);
                if(list.size()==10){
                    message.setNum(1);
                    message.setMsg("用户已经装备的装备超过十件");
                }else{
                    message.setNum(2);
                    message.setMsg("用户已经装备的装备没有超过十件");
                }
            }
        }else {
            message.setNum(0);
            message.setMsg("用户没有背包");
        }
        return message;
    }

    /**
     * 根据装备的主键卸下装备
     * @param equipmentId
     */
    @ApiOperation(value = "根据装备的主键卸下装备",notes = "成功则返回成功")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "equipmentId",value = "装备主键",required = true),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "knapsackId",value = "背包主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "boxId",value = "宝箱主键"),
            @ApiImplicitParam(paramType = "query",dataType = "Integer",name = "equipmentLevel",value = "装备等级"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "equipmentImage",value = "装备图片"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp1",value = "我的装备 1为装备上了 2为背包装备"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp2",value = "道具50金币 穿戴品只有5金币"),
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "exp3",value = "1为穿戴品 2为道具")
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @GetMapping("/updateExp1")
    @CrossOrigin
    @CheckToken
    public void updateExp1(Long equipmentId){
        if( StringUtils.isEmpty(equipmentId) ){
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        tbEquipmentService.updateExp1(equipmentId);
    }
}
