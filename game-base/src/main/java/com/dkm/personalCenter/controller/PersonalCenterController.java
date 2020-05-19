package com.dkm.personalCenter.controller;

import com.dkm.feign.fallback.ResourceFeignClientFallback;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @PROJECT_NAME: game_project
 * @DESCRIPTION: 个人中心接口
 * @USER: 周佳佳
 * @DATE: 2020/5/19 11:05
 */
@RequestMapping("/dkm/PersonalCenterController")
@RestController
@Api(description = "个人中心的接口文档")
public class PersonalCenterController {
    @Autowired
    LocalUser localUser;
    @Autowired
    ResourceFeignClientFallback resourceFeignClientFallback;

    @GetMapping("/selectAll")
    @CrossOrigin
    //@CheckToken
    public Map<String,Object> selectAll(){
        Map<String,Object> map=new HashMap<>();
        //装备的map
        map.put("equipment",resourceFeignClientFallback.userCenter());
        return map;
    }
}
