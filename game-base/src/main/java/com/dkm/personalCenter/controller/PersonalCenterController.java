package com.dkm.personalCenter.controller;

import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.feign.FamilyFeiginClient;
import com.dkm.feign.ResourceFeignClient;
import com.dkm.feign.UserFeignClient;
import com.dkm.feign.fallback.ResourceFeignClientFallback;
import com.dkm.feign.fallback.UserFeignClientFallback;
import com.dkm.gift.service.GiftService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.medal.service.MedalService;
import com.dkm.mounts.service.MountService;
import com.dkm.personalCenter.domain.vo.UserInfoQueryBoVo;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    @Resource
    ResourceFeignClient resourceFeignClient;
    @Resource
    UserFeignClient userFeignClient;
    @Resource
    private MedalService medalService;
    @Resource
    private MountService mountService;
    @Resource
    private GiftService giftService;
    @Resource
    private FamilyFeiginClient familyFeiginClient;

    @ApiOperation(value = "个人中心的查询接口",notes = "equipment 为装备的数据 blackHouse 为黑屋的用户信息对象 Seed为查询用户解锁的种子" +
            "  queryMySkill 查询我的技能  AttendantGoods 查询跟班产出的产物  queryUser 为用户总体力和当前体力" +
            "queryAidUser 为用户的主人")
    @GetMapping("/selectAll")
    @CrossOrigin
    @CheckToken
    public Map<String,Object> selectAll(){
        Map<String,Object> map=new HashMap<>(6);
        UserLoginQuery user = localUser.getUser();
        //黑屋的用户信息对象
        map.put("personal",resourceFeignClient.personalCenterAll(user.getId()));

        //查询出用户的总体力和当前体力
     /*   Result<UserInfoQueryBo> userInfoQueryBoResult = userFeignClient.queryUser(user.getId());
        UserInfoQueryBo data = userInfoQueryBoResult.getData();
        UserInfoQueryBoVo userInfoQueryBoVo=new UserInfoQueryBoVo();
        BeanUtils.copyProperties(data,userInfoQueryBoVo);
        Map<String,Object> mapTwo=new HashMap<>(3);
        mapTwo.put("code",0);
        mapTwo.put("msg","操作成功");
        mapTwo.put("data",userInfoQueryBoVo);

        map.put("queryUser",mapTwo);*/
        //用户勋章数
        map.put("medalNumber",medalService.getUserMadelNumber(user.getId()));
        //座驾信息
        map.put("mounts",mountService.getUserCenterMounts(user.getId()));
        //礼物信息
        map.put("gift",giftService.getUserCenterGift(user.getId()));
     /*   //家族信息
        map.put("family",familyFeiginClient.getUserCenterFamily(user.getId()));*/
        return map;

    }
    @ApiOperation(value = "根据用户id查询个人中心数据",notes = "equipment 为装备的数据 blackHouse 为黑屋的用户信息对象 Seed为查询用户解锁的种子" +
            "  queryMySkill 查询我的技能  AttendantGoods 查询跟班产出的产物  queryUser 为用户总体力和当前体力" +
            "queryAidUser 为用户的主人")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "userId",value = "用户主键",required = true),
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
    public Map<String,Object> findById(@PathVariable("userId") Long userId){
        Map<String,Object> map=new HashMap<>(16);

        map.put("personal",resourceFeignClient.personalCenterAll(userId));

        //查询出用户的总体力和当前体力
     /*   Result<UserInfoQueryBo> userInfoQueryBoResult = userFeignClient.queryUser(userId);
        UserInfoQueryBo data = userInfoQueryBoResult.getData();
        UserInfoQueryBoVo userInfoQueryBoVo=new UserInfoQueryBoVo();
        BeanUtils.copyProperties(data,userInfoQueryBoVo);
        Map<String,Object> mapTwo=new HashMap<>(2);
        mapTwo.put("code",0);
        mapTwo.put("msg","操作成功");
        mapTwo.put("data",userInfoQueryBoVo);

        map.put("queryUser",mapTwo);*/
        //用户勋章数
        map.put("medalNumber",medalService.getUserMadelNumber(userId));
        //座驾信息
        map.put("mounts",mountService.getUserCenterMounts(userId));
        //礼物信息
        map.put("gift",giftService.getUserCenterGift(userId));
        //家族信息
/*        map.put("family",familyFeiginClient.getUserCenterFamily(userId));*/
        return map;
    }
}
