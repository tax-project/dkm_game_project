package com.dkm.personalCenter.controller;

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
    ResourceFeignClientFallback resourceFeignClientFallback;

    @ApiOperation(value = "个人中心的查询接口",notes = "equipment 为装备的数据")
    @GetMapping("/selectAll")
    @CrossOrigin
    //@CheckToken
    public Map<String,Object> selectAll(){
        System.out.println("===y有东西吗");
        Map<String,Object> map=new HashMap<>();
        //装备的map
        map.put("equipment",resourceFeignClientFallback.userCenter());
        map.put("selectIsBlackTwo",resourceFeignClientFallback.selectIsBlackTwo());
        map.put("equipment222",resourceFeignClientFallback.selectUserIdAndFoodId(709866088598507520L));
        System.out.println("=="+map.get("selectIsBlackTwo"));
        return map;
    }
}
