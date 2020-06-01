package com.dkm.personalCenter.controller;

import com.dkm.feign.ResourceFeignClient;
import com.dkm.feign.UserFeignClient;
import com.dkm.feign.fallback.ResourceFeignClientFallback;
import com.dkm.feign.fallback.UserFeignClientFallback;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    ResourceFeignClient resourceFeignClient;
    @Autowired
    UserFeignClient userFeignClient;
    @ApiOperation(value = "个人中心的查询接口",notes = "equipment 为装备的数据 blackHouse 为黑屋的用户信息对象 Seed为查询用户解锁的种子" +
            "  queryMySkill 查询我的技能  AttendantGoods 查询跟班产出的产物  queryUser 为用户总体力和当前体力")
    @GetMapping("/selectAll")
    @CrossOrigin
    @CheckToken
    public Map<String,Object> selectAll(){
        Map<String,Object> map=new HashMap<>(6);
        //装备的map
        map.put("equipment",resourceFeignClient.userCenterTwo(localUser.getUser().getId()));
        //黑屋的用户信息对象
        map.put("blackHouse",resourceFeignClient.selectIsBlackTwo(localUser.getUser().getId()));
        //查询用户解锁的种子
        map.put("Seed",resourceFeignClient.queryAreUnlocked(localUser.getUser().getId()));
        //查询我的技能
        map.put("queryMySkill",resourceFeignClient.queryAllSkillByUserId(localUser.getUser().getId()));
        //查询跟班产出的产物
        map.put("AttendantGoods",resourceFeignClient.queryJoinOutPutGoods(localUser.getUser().getId()));
        //查询出用户的总体力和当前体力
        map.put("queryUser",userFeignClient.queryUser(localUser.getUser().getId()));
        return map;
    }
}
