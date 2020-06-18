package com.dkm.union.controller;

import com.dkm.jwt.islogin.CheckToken;
import com.dkm.union.service.IUnionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-06-18 10:43
 **/
@Api(tags = "工会API")
@RestController
@RequestMapping("/union")
public class UnionController {


    @Resource
    private IUnionService iUnionService;

    @ApiOperation("我的工会")
    @GetMapping("/unionInfo")
    @ApiImplicitParam(value = "工会id",name="unionId",paramType = "path",dataType = "Long",required = true)
    @CheckToken
    @CrossOrigin
    public Map<String,Object> familyInfo(Long unionId){
        return null;
    }


}
