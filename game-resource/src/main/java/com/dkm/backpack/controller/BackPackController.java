package com.dkm.backpack.controller;

import com.dkm.backpack.entity.bo.AddGoodsInfo;
import com.dkm.backpack.entity.bo.SellGoodsInfo;
import com.dkm.backpack.entity.vo.*;
import com.dkm.backpack.service.IBackpackService;
import com.dkm.backpack.service.IEquipmentService;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.knapsack.service.ITbEquipmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @program: game_project
 * @description: 背包
 * @author: zhd
 * @create: 2020-07-17 20:38
 **/
@Api(tags = "背包api")
@RestController
@RequestMapping("/backpack")
public class BackPackController {

    @Resource
    private IBackpackService backpackService;

    @Resource
    private IEquipmentService equipmentService;

    @Resource
    private LocalUser localUser;

    @ApiOperation(value = "获取背包物品")
    @GetMapping("/getUserBackpackGoods")
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @CrossOrigin
    @CheckToken
    public List<UserBackpackGoodsVo> getUserBackpackGoods(){
        return  backpackService.getUserBackpackGoods(localUser.getUser().getId());
    }

    @ApiOperation(value = "（fegin调用）增加减少背包数量")
    @PostMapping("/addBackpackGoods")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", required = true, dataType = "long", value = "用户id"),
            @ApiImplicitParam(paramType = "query", name = "goodId", required = true, dataType = "long", value = "物品id"),
            @ApiImplicitParam(paramType = "query", name = "number", required = true, dataType = "int", value = "数量 正数增长 ， 负数减少")
    })
    @CrossOrigin
    public void addBackpackGoods(@RequestBody AddGoodsInfo addGoodsInfo){
        if(addGoodsInfo==null||addGoodsInfo.getGoodId()==null||addGoodsInfo.getNumber()==0){
            throw new ApplicationException(CodeType.PARAMETER_ERROR);
        }
        backpackService.addBackpackGoods(addGoodsInfo);
    }

    @ApiOperation(value = "背包物品出售接口")
    @PostMapping("/sellBackpackGoods")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Token", required = true, dataType = "string", value = "token"),
            @ApiImplicitParam(paramType = "query", name = "backpackId", required = true, dataType = "long", value = "物品id"),
            @ApiImplicitParam(paramType = "query", name = "number", required = true, dataType = "int", value = "出售数量")
    })
    @CrossOrigin
    @CheckToken
    public void sellBackpackGoods(@RequestBody SellGoodsInfo sellGoodsInfo){
        if(sellGoodsInfo==null||sellGoodsInfo.getNumber()<=0||sellGoodsInfo.getBackpackId()==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR);
        }
        sellGoodsInfo.setUserId(localUser.getUser().getId());
        backpackService.sellBackpackGoods(sellGoodsInfo);
    }

    @ApiOperation(value = "获取装备详情接口")
    @GetMapping("/equipmentInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Token", required = true, dataType = "string", value = "token"),
            @ApiImplicitParam(paramType = "path", name = "backpackId", required = true, dataType = "long", value = "背包id")
    })
    @CrossOrigin
    @CheckToken
    public Map<String,EquipmentVo> equipmentInfo(@RequestParam("backpackId") Long backpackId){
        if(backpackId==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR);
        }
        return equipmentService.getEquipmentInfo(localUser.getUser().getId(),backpackId);
    }

    @ApiOperation(value = "获取用户当前所有已装备数据")
    @GetMapping("/getUserEquipment")
    @ApiImplicitParam(paramType = "header", name = "Token", required = true, dataType = "string", value = "token")
    @CrossOrigin
    @CheckToken
    public List<UserEquipmentVo> getUserEquipment(){
        return equipmentService.getUserEquipment(localUser.getUser().getId());
    }

    @ApiOperation(value = "装备当前装备（已装备则卸下该装备）")
    @GetMapping("/removeOrEquipment")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Token", required = true, dataType = "string", value = "token"),
            @ApiImplicitParam(paramType = "path", name = "backpackId", required = true, dataType = "long", value = "背包id")
    })
    @CrossOrigin
    @CheckToken
    public void removeOrEquipment(@RequestParam("backpackId") Long backpackId){
        if(backpackId==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR);
        }
        equipmentService.removeOrEquipment(localUser.getUser().getId(),backpackId);
    }

    @ApiOperation(value = "获取金星星数量")
    @GetMapping("/getStars")
    @ApiImplicitParam(paramType = "header", name = "Token", required = true, dataType = "string", value = "token")
    @CrossOrigin
    @CheckToken
    public GoldStarVo getStars(){
        return backpackService.getStar(localUser.getUser().getId());
    }

    @ApiOperation(value = "获取用户食物信息")
    @GetMapping("/getFoods")
    @ApiImplicitParam(paramType = "header", name = "Token", required = true, dataType = "string", value = "token")
    @CrossOrigin
    @CheckToken
    public List<FoodInfoVo> getFoods(){
        return  backpackService.getFood(localUser.getUser().getId());
    }

    @ApiOperation(value = "(fegin调用食物信息)")
    @GetMapping("/getFoodsFegin")
    @CrossOrigin
    public List<FoodInfoVo> getFoodsFegin(@RequestParam("userId") Long userId){
        return  backpackService.getFood(userId);
    }

}