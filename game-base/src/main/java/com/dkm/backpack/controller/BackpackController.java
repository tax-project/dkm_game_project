package com.dkm.backpack.controller;

import com.dkm.backpack.entity.bo.AddBackpackItemBO;
import com.dkm.backpack.service.IBackpackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: HuangJie
 * @Date: 2020/5/14 14:07
 * @Version: 1.0V
 */
@Api(tags = "背包API")
@RestController
@RequestMapping("/v1/backpack")
public class BackpackController {

    @Autowired
    private IBackpackService backpackService;

    @PostMapping("/add/backpack/item")
    @ApiOperation(value = "添加背包物品",notes = "添加背包物品",produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "用户token",dataType = "String",paramType = "header",required = true),
            @ApiImplicitParam(name = "itemId",value = "物品的ID",dataType = "Long",paramType = "body",required = true),
            @ApiImplicitParam(name = "itemTableNumber",value = "物品所在表的编号，食物为1，装备为2，衣服为3",dataType = "Integer",required = true,paramType = "body"),
            @ApiImplicitParam(name = "itemNumber",value = "物品数量",dataType = "Integer",required = true,paramType = "body")
    })
    public void addBackpackItem(@RequestBody AddBackpackItemBO backpackItemBO){
        backpackService.addBackpackItem(backpackItemBO);
    }

}
