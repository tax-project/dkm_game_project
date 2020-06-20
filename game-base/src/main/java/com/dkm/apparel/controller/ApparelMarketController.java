package com.dkm.apparel.controller;

import com.dkm.apparel.service.IApparelMarketService;
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

/**
 * @program: game_project
 * @description: 集市
 * @author: zhd
 * @create: 2020-06-19 15:59
 **/
@Api(tags = "服饰集市")
@RestController
@RequestMapping("/apparelMarket")
public class ApparelMarketController {

    @Resource
    private IApparelMarketService apparelMarketService;

    @Resource
    private LocalUser localUser;

    @ApiOperation("上架服饰")
    @ApiImplicitParam(value = "apparelId",name="服饰id",paramType = "path",dataType = "Long",required = true)
    @CrossOrigin
    @CheckToken
    @PostMapping("/putOnSell")
    public void putOnSell(@RequestBody Long apparelId){
        if(apparelId==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR);
        }
        apparelMarketService.putOnSell(localUser.getUser().getId(),apparelId);
    }

}
