package com.dkm.personalCenter.controller;

import com.dkm.feign.ResourceFeignClient;
import com.dkm.feign.UserFeignClient;
import com.dkm.feign.fallback.ResourceFeignClientFallback;
import com.dkm.feign.fallback.UserFeignClientFallback;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.islogin.CheckToken;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            "  queryMySkill 查询我的技能  AttendantGoods 查询跟班产出的产物  queryUser 为用户总体力和当前体力" +
            "queryAidUser 为用户的主人")
    @GetMapping("/selectAll")
    @CrossOrigin
    @CheckToken
    public Map<String,Object> selectAll(@PathVariable("userId") String userId){
        Map<String,Object> map=new HashMap<>(6);
        if(userId!=null){
            //黑屋的用户信息对象
            map.put("blackHouse",resourceFeignClient.selectIsBlackTwo(localUser.getUser().getId()));
            map.put("personal",resourceFeignClient.PersonalCenterAll(localUser.getUser().getId()));
            //查询出用户的总体力和当前体力
            map.put("queryUser",userFeignClient.queryUser(localUser.getUser().getId()));

       /* //查询用户解锁的种子
        map.put("Seed",resourceFeignClient.queryAreUnlocked(localUser.getUser().getId()));
        //查询我的技能
        map.put("queryMySkill",resourceFeignClient.queryAllSkillByUserId(localUser.getUser().getId()));
        //查询跟班产出的产物
        map.put("AttendantGoods",resourceFeignClient.queryJoinOutPutGoods(localUser.getUser().getId()));
          map.put("queryAidUser",resourceFeignClient.queryAidUser(localUser.getUser().getId()));*/

        //查询出用户的主人
            return map;
        }
        return null;

    }
    @ApiOperation(value = "根据用户id查询个人中心数据",notes = "equipment 为装备的数据 blackHouse 为黑屋的用户信息对象 Seed为查询用户解锁的种子" +
            "  queryMySkill 查询我的技能  AttendantGoods 查询跟班产出的产物  queryUser 为用户总体力和当前体力" +
            "queryAidUser 为用户的主人")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "String",name = "userId",value = "用户主键",required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 401,message="没有权限"),
            @ApiResponse(code = 403,message = "服务器拒绝请求"),
            @ApiResponse(code = 404,message="请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 500,message="后台报错"),
            @ApiResponse(code = 200,message="返回成功")
    })
    @GetMapping("/findById/{userId}")
    @CrossOrigin
    @CheckToken
    public Map<String,Object> findById(@PathVariable("userId") String userId){
        Map<String,Object> map=new HashMap<>(6);
        //黑屋的用户信息对象
        map.put("blackHouse",resourceFeignClient.selectIsBlackTwo(Long.valueOf(userId)));

        map.put("personal",resourceFeignClient.PersonalCenterAll(Long.valueOf(userId)));

        //查询出用户的总体力和当前体力
        map.put("queryUser",userFeignClient.queryUser(Long.valueOf(userId)));

        //查询用户解锁的种子
        //map.put("Seed",resourceFeignClient.queryAreUnlocked(Long.valueOf(userId)));
        //查询我的技能
       // map.put("queryMySkill",resourceFeignClient.queryAllSkillByUserId(Long.valueOf(userId)));
        //查询跟班产出的产物
        //map.put("AttendantGoods",resourceFeignClient.queryJoinOutPutGoods(Long.valueOf(userId)));
        //查询出用户的主人
        //map.put("queryAidUser",resourceFeignClient.queryAidUser(Long.valueOf(userId)));


        return map;
    }
}
