package com.dkm.gift.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.gift.entity.GiftEntity;
import com.dkm.gift.service.GiftService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: game_project
 * @description: 送礼
 * @author: zhd
 * @create: 2020-05-27 09:20
 **/
@Api(tags = "礼物API")
@RestController
@RequestMapping("/gift")
public class GiftController {

    @Resource
    private LocalUser localUser;

    @Resource
    private GiftService giftService;

    @ApiOperation("获取礼物列表")
    @ApiImplicitParam(name = "type",value = "0金币1钻石",required = true,paramType = "int",dataType = "path")
    @GetMapping("/getGift")
    @CrossOrigin
    @CheckToken
    public List<GiftEntity> getGift(Integer type){
        if(type==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR);
        }
        return giftService.getAllGift(type);
    }

}
