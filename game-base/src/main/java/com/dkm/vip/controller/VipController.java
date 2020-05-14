package com.dkm.vip.controller;


import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.vip.service.VipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
/**
 * @author zhd
 * @date 2020/5/8 13:44
 */
@Api(tags = "vip模块")
@RestController
@RequestMapping("/vip")
public class VipController {

    @Resource
    private VipService vipService;

    /***
     * 开通 vip
     * @param userId
     * @param money
     * @return
     */
    @ApiOperation("开通vip")
    @ApiImplicitParam(name = "money", value = "支付金额", required = true, dataType = "BigDecimal", paramType = "path")
    @PostMapping(value = "/openVip")
    @CrossOrigin
    public void openVip(Long userId, BigDecimal money){
        if(money.compareTo(BigDecimal.ZERO)<=0){
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        if(!vipService.openVip(userId, money)){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"开通失败");
        };
    }
}
