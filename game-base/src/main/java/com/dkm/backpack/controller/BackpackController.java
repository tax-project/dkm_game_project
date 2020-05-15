package com.dkm.backpack.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/add/backpack/item")
    @ApiOperation(value = "添加背包物品",notes = "添加背包物品",produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "itemId",value = "物品的ID",dataType = "Long",paramType = "body",required = true),
            @ApiImplicitParam(name = "itemTableName",value = "物品所在表名称",dataType = "String",required = true,paramType = "body")
    })
    public void addBackpackItem(){
        
    }

}
