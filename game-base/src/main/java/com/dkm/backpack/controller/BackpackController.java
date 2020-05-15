package com.dkm.backpack.controller;

import com.dkm.backpack.service.IBackpackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/add/backpack/item")
    @ApiOperation(value = "获取背包物品",notes = "添加背包物品",produces = "application/json")
    @ApiImplicitParam(name = "token",value = "用户token",dataType = "String",paramType = "header",required = true)
    public void addBackpackItem(){
        backpackService.allBackpackItem();
    }

}
