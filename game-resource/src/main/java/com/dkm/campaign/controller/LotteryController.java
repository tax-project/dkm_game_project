package com.dkm.campaign.controller;


import com.dkm.campaign.entity.vo.LotteryInfoVo;
import com.dkm.campaign.service.ILotteryService;
import com.dkm.jwt.islogin.CheckToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 活动下的神秘商店
 *
 * @author OpenE
 * @version 1.1
 */
@Api(tags = {"神秘商店 API", "活动"})
@RequestMapping("/campaign/lottery/")
@RestController
public class LotteryController {
    @Resource
    private ILotteryService lotteryService;


    @ApiOperation("获取神秘商店下的所有信息")
    @CrossOrigin
    @CheckToken
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @GetMapping(value = "/getMineInfo", produces = "application/json")
    public LotteryInfoVo getAllInfo() {
        return lotteryService.getAllInfo();
    }
}
