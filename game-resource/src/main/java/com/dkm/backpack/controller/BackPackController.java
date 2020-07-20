package com.dkm.backpack.controller;

import com.dkm.backpack.entity.bo.AddGoodsInfo;
import com.dkm.backpack.entity.bo.SellGoodsInfo;
import com.dkm.backpack.entity.vo.UserBackpackGoodsVo;
import com.dkm.backpack.service.IBackpackService;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    private LocalUser localUser;

    @ApiOperation(value = "获取宝箱列表")
    @GetMapping("/selectBoxInfo")
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
        addGoodsInfo.setUserId(localUser.getUser().getId());
        backpackService.addBackpackGoods(addGoodsInfo);
    }

    @ApiOperation(value = "背包物品出售接口")
    @PostMapping("/sellBackpackGoods")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Token", required = true, dataType = "string", value = "token"),
            @ApiImplicitParam(paramType = "query", name = "goodId", required = true, dataType = "long", value = "物品id"),
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

    @ApiOperation(value = "装备详情接口")
    @GetMapping("/equipmentInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "Token", required = true, dataType = "string", value = "token"),
            @ApiImplicitParam(paramType = "path", name = "backpackId", required = true, dataType = "long", value = "背包id")
    })
    @CrossOrigin
    @CheckToken
    public void equipmentInfo(@RequestParam("backpackId") Long backpackId){
        if(backpackId==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR);
        }

    }
}
