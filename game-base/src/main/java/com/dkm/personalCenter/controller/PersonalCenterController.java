package com.dkm.personalCenter.controller;

import com.dkm.feign.ResourceFeignClient;
import com.dkm.feign.fallback.ResourceFeignClientFallback;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.personalCenter.domain.vo.TbEquipmentKnapsackVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
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

    @ApiOperation(value = "个人中心的查询接口",notes = "equipment 为装备的数据 blackHouse 为黑屋的用户信息对象 Seed为查询用户解锁的种子" +
            "  queryMySkill 查询我的技能   查询跟班产出的产物 AttendantGoods")
    @GetMapping("/selectAll")
    @CrossOrigin
    //@CheckToken
    public Map<String,Object> selectAll(){
        Map<String,Object> map=new HashMap<>();
        //装备的map
        map.put("equipment",resourceFeignClient.userCenter());
        //黑屋的用户信息对象
        map.put("blackHouse",resourceFeignClient.selectIsBlackTwo());
        //查询用户解锁的种子
        map.put("Seed",resourceFeignClient.queryAreUnlocked());
        //查询我的技能
        map.put("queryMySkill",resourceFeignClient.queryMySkill());
        //查询跟班产出的产物
        map.put("AttendantGoods",resourceFeignClient.queryJoinOutPutGoods());
        return map;
    }
}
