package com.dkm.family.controller;

import com.dkm.family.service.FamilyService;
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
 * @description: 家族
 * @author: zhd
 * @create: 2020-05-20 09:45
 **/
@Api(tags = "家族API")
@RestController
@RequestMapping("/family")
public class FamilyController {
    @Resource
    private LocalUser localUser;
    @Resource
    private FamilyService familyService;

    @ApiOperation("创建家族")
    @ApiImplicitParam(value = "家族名称",name="name",paramType = "path",dataType = "String",required = true)
    @PostMapping("/createFamily")
    @CheckToken
    @CrossOrigin
    public void createFamily(@RequestBody String name){
        familyService.creatFamily(localUser.getUser().getId(),name);
    }
}
