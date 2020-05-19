package com.dkm.personalCenter.controller;

import io.swagger.annotations.Api;
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
@RequestMapping("/dkm/PersonalCenter")
@RestController
@Api(description = "个人中心的接口文档")
public class PersonalCenter {

    public Map<String,Object> selectAll(){
        Map<String,Object> map=new HashMap<>();

        return map;
    }
}
