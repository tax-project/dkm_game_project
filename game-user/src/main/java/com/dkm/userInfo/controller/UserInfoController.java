package com.dkm.userInfo.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.userInfo.entity.bo.IncreaseUserInfoBO;
import com.dkm.userInfo.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author qf
 * @date 2020/5/14
 * @vesion 1.0
 **/
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

   @PostMapping("/increase/all")
   public void increaseUserInfoGold(@RequestBody IncreaseUserInfoBO increaseUserInfoBO){
         userInfoService.increaseUserInfo(increaseUserInfoBO);
   }
}
