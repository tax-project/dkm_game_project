package com.dkm.mallEvents.controller;

import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.mallEvents.entities.data.CancerGiftBoxInfoVo;
import com.dkm.mallEvents.entities.data.LuckyGiftVo;
import com.dkm.mallEvents.entities.data.RechargeVo;
import com.dkm.mallEvents.service.ICancerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "商城 API （巨蟹座）")
@RestController("/event/cancer/")
public class CancerController {


    @Resource
    private LocalUser localUser;

    @Resource
    private ICancerService cancerService;

    @ApiOperation("获取单笔充值满30领取豪华礼包的接口")
    @CrossOrigin
    @CheckToken
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @GetMapping(value = "/getSingleTopUpInfo", produces = "application/json")
    public RechargeVo getSingleTopUp() {
        return cancerService.getSingleTopUp(localUser.getUser().getId());
    }

    @ApiOperation("领取单笔充值满30领取豪华礼包")
    @CrossOrigin
    @CheckToken
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token"),
            @ApiImplicitParam(paramType = "path", name = "id", required = true, dataType = "Long", value = "领取充值奖励的id")
    })
    @GetMapping(value = "/getSingleTopUpInfo/{id}/check", produces = "application/json")
    public Boolean getSingleTopUpInfoCheck(@PathVariable Integer id) {
        return cancerService.getSingleTopUpInfoCheck(localUser.getUser().getId(), id);
    }



    @ApiOperation("充值送蓝卷")
    @CrossOrigin
    @CheckToken
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @GetMapping(value = "/rechargeTheBlueRoll", produces = "application/json")
    public RechargeVo getRechargeTheBlueRoll() {
        return cancerService.getRechargeTheBlueRoll(localUser.getUser().getId());
    }


    @ApiOperation("充值送蓝卷领取接口")
    @CrossOrigin
    @CheckToken
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token"),
            @ApiImplicitParam(paramType = "path", name = "id", required = true, dataType = "Long", value = "领取充值奖励的id")
    })
    @GetMapping(value = "/rechargeTheBlueRoll/{id}/check", produces = "application/json")
    public Boolean rechargeTheBlueRollCheck(@PathVariable Integer id) {
        return cancerService.rechargeTheBlueRollCheck(localUser.getUser().getId(), id);
    }


    @ApiOperation("累计充值/消费大返利的累计充值查询")
    @CrossOrigin
    @CheckToken
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @GetMapping(value = "/cumulativeRecharge", produces = "application/json")
    public RechargeVo getCumulativeRecharge() {
        return cancerService.getCumulativeRecharge(localUser.getUser().getId());
    }

    @ApiOperation("领取充值的奖励")
    @CrossOrigin
    @CheckToken
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @GetMapping(value = "/cumulativeRecharge/{id}/receive", produces = "application/json")
    public Boolean receiveTopUpReward(@PathVariable Integer id) {
        return cancerService.receiveTopUpReward(localUser.getUser().getId(), id);
    }


    @ApiOperation("累计充值/消费大返利的累计消费查询")
    @CrossOrigin
    @CheckToken
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @GetMapping(value = "/consumerRecharge", produces = "application/json")
    public RechargeVo getConsumerRecharge() {
        return cancerService.getConsumerRecharge(localUser.getUser().getId());
    }

    @ApiOperation("领取消费的奖励")
    @CrossOrigin
    @CheckToken
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @GetMapping(value = "/consumerRecharge/{id}/receive", produces = "application/json")
    public Boolean receiveConsumerReward(@PathVariable Integer id) {
        return cancerService.receiveConsumerReward(localUser.getUser().getId(), id);
    }

    @ApiOperation("消耗有礼查询 [金星星]")
    @CrossOrigin
    @CheckToken
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @GetMapping(value = "/consumption/king/info", produces = "application/json")
    public RechargeVo consumptionKing() {
        return cancerService.consumptionKing(localUser.getUser().getId());
    }

    @ApiOperation("消耗有礼领取 [金星星] ")
    @CrossOrigin
    @CheckToken
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @GetMapping(value = "/consumption/king/{id}/receive", produces = "application/json")
    public Boolean consumptionKingReward(@PathVariable Integer id) {
        return cancerService.consumptionKingReward(localUser.getUser().getId(), id);
    }

    @ApiOperation("消耗有礼查询 [紫星星]")
    @CrossOrigin
    @CheckToken
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @GetMapping(value = "/consumption/purple/info", produces = "application/json")
    public RechargeVo consumptionPurple() {
        return cancerService.consumptionPurple(localUser.getUser().getId());
    }

    @ApiOperation("消耗有礼领取 [紫星星] ")
    @CrossOrigin
    @CheckToken
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @GetMapping(value = "/consumption/purple/{id}/receive", produces = "application/json")
    public Boolean consumptionKingPurple(@PathVariable Integer id) {
        return cancerService.consumptionKingPurple(localUser.getUser().getId(), id);
    }

    @ApiOperation("消耗有礼查询 [蓝劵]")
    @CrossOrigin
    @CheckToken
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @GetMapping(value = "/consumption/blue/info", produces = "application/json")
    public RechargeVo consumptionBlue() {
        return cancerService.consumptionBlue(localUser.getUser().getId());
    }

    @ApiOperation("消耗有礼领取 [蓝劵] ")
    @CrossOrigin
    @CheckToken
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @GetMapping(value = "/consumption/blue/{id}/receive", produces = "application/json")
    public Boolean consumptionKingBlue(@PathVariable Integer id) {
        return cancerService.consumptionKingBlue(localUser.getUser().getId(), id);
    }

    @ApiOperation("消耗有礼查询 [紫劵]")
    @CrossOrigin
    @CheckToken
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @GetMapping(value = "/consumption/PurpleOffer/info", produces = "application/json")
    public RechargeVo consumptionPurpleOffer() {
        return cancerService.consumptionPurpleOffer(localUser.getUser().getId());
    }

    @ApiOperation("消耗有礼领取 [紫劵] ")
    @CrossOrigin
    @CheckToken
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @GetMapping(value = "/consumption/PurpleOffer/{id}/receive", produces = "application/json")
    public Boolean consumptionKingPurpleOffer(@PathVariable Integer id) {
        return cancerService.consumptionKingPurpleOffer(localUser.getUser().getId(), id);
    }


    @ApiOperation("消耗有礼查询 [橙劵]")
    @CrossOrigin
    @CheckToken
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @GetMapping(value = "/consumption/Orange/info", produces = "application/json")
    public RechargeVo consumptionOrange() {
        return cancerService.consumptionOrange(localUser.getUser().getId());
    }

    @ApiOperation("消耗有礼领取 [橙劵] ")
    @CrossOrigin
    @CheckToken
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @GetMapping(value = "/consumption/Orange/{id}/receive", produces = "application/json")
    public Boolean consumptionKingOrange(@PathVariable Integer id) {
        return cancerService.consumptionKingOrange(localUser.getUser().getId(), id);
    }

    @ApiOperation("幸运礼物")
    @CrossOrigin
    @CheckToken
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @GetMapping(value = "/luckyGift/getAll", produces = "application/json")
    public LuckyGiftVo getLuckyGift() {
        return cancerService.getLuckyGiftInfo();
    }

    @ApiOperation("巨蟹礼盒")
    @CrossOrigin
    @CheckToken
    @ApiImplicitParam(paramType = "header", name = "TOKEN", required = true, dataType = "String", value = "请求的Token")
    @GetMapping(value = "/cancerGiftBox/getInfo", produces = "application/json")
    public CancerGiftBoxInfoVo getCancerGiftBoxInfo() {
        return cancerService.getCancerGiftBoxInfo();
    }

}
