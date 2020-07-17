package com.dkm.campaign.controller;


import com.dkm.campaign.entity.vo.LotteryBuyResultVo;
import com.dkm.campaign.entity.vo.LotteryInfoVo;
import com.dkm.campaign.service.ILotteryService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

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
    private Object userId;


    @ApiOperation("获取神秘商店下的所有信息")
    @CrossOrigin
    @CheckToken
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @GetMapping(value = "/getMineInfo", produces = "application/json")
    public LotteryInfoVo getAllInfo() {
        return lotteryService.getAllInfo(getUserId());
    }

    @ApiOperation("获取神秘商店下的所有信息")
    @CrossOrigin
    @CheckToken
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token"),
            @ApiImplicitParam(paramType = "path", name = "lotteryId", required = true, dataType = "Long", value = "选中的奖池的id"),
            @ApiImplicitParam(paramType = "path", name = "size", required = true, dataType = "Int", value = "购买的数目")
    })
    @GetMapping(value = "/{lotteryId}/buy/{size}", produces = "application/json")
    public LotteryBuyResultVo buy(@PathVariable Long lotteryId, @PathVariable Integer size) {
        return lotteryService.buy(lotteryId, size, getUserId());
    }


    @Resource
    private LocalUser localUser;


    public Long getUserId() {
        return localUser.getUser().getId();
    }
}
