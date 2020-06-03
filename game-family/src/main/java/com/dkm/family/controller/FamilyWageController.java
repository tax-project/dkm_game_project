package com.dkm.family.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.family.service.FamilyService;
import com.dkm.family.service.FamilyWageService;
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
import java.util.Map;

/**
 * @program: game_project
 * @description: 家族工资
 * @author: zhd
 * @create: 2020-06-03 11:08
 **/
@Api(tags = "家族工资发放API")
@RestController
@RequestMapping("/familyWage")
public class FamilyWageController {

    @Resource
    private LocalUser localUser;
    @Resource
    private FamilyWageService familyWageService;

    @ApiOperation("获取工资列表")
    @GetMapping("/getFamilyWake")
    @CrossOrigin
    @CheckToken
    public Map<String, Object> getFamilyWake(){
        return familyWageService.getWageList(localUser.getUser().getId());
    }

    @ApiOperation("领取当天工资")
    @GetMapping("/receiveWake")
    @CrossOrigin
    @CheckToken
    public void receiveWake(){
    }

}
