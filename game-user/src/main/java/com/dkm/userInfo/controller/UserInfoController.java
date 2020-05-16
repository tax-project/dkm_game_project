package com.dkm.userInfo.controller;

import com.alibaba.fastjson.JSONObject;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.userInfo.service.IUserInfoService;
import com.dkm.wechat.util.BodyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qf
 * @date 2020/5/14
 * @vesion 1.0
 **/
@Api(tags = "用户详细信息API")
@RestController
@RequestMapping("/v1/userInfo")
public class UserInfoController {

   @Autowired
   private IUserInfoService userInfoService;

   @GetMapping("/updateMuch/{much}/{userId}")
   public void updateUserInfo (@PathVariable("much") Integer much, @PathVariable("userId") Long userId) {
      if (userId == null) {
         throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
      }
      userInfoService.updateUserInfo(much,userId);
   }

   @PostMapping("/increase/gold")
   @CheckToken
   @ApiOperation(value = "添加用户金币",notes = "添加他用户金币",produces = "application/json")
   @ApiImplicitParam(name = "goldNumber",value = "金币数目",dataType = "Double",paramType = "body",required = true)
   public void increaseUserInfoGold(HttpServletRequest request){
      JSONObject json = BodyUtils.bodyJson(request);
      Double goldNumber = json.getDouble("goldNumber");
      if (goldNumber==null||goldNumber==0){
         throw new ApplicationException(CodeType.SERVICE_ERROR, "参数不能为空");
      }
      userInfoService.increaseUserInfoGold(goldNumber);
   }
}
