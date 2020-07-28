package com.dkm.mallEvents.controller;

import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.mallEvents.entities.vo.SingleTopUpVo;
import com.dkm.mallEvents.service.ICancerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
    public List<SingleTopUpVo> getSingleTopUp() {
        return cancerService.getSingleTopUp(localUser.getUser().getId());
    }


}
