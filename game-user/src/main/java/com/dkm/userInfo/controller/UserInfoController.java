package com.dkm.userInfo.controller;

import com.dkm.constanct.CodeType;
import com.dkm.entity.bo.UserPlunderBo;
import com.dkm.exception.ApplicationException;
import com.dkm.userInfo.entity.bo.IncreaseUserInfoBO;
import com.dkm.userInfo.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

   @PostMapping("/increase")
   public void increaseUserInfo(@RequestBody IncreaseUserInfoBO increaseUserInfoBO){
         userInfoService.increaseUserInfo(increaseUserInfoBO);
   }

   @PostMapping("/cut")
   public void cutUserInfo(@RequestBody IncreaseUserInfoBO increaseUserInfoBO){
      userInfoService.cutUserInfo(increaseUserInfoBO);
   }

   @GetMapping("/listUserPlunder")
   public List<UserPlunderBo> listUserPlunder(){
      return userInfoService.listUserPlunder();
   }

   @GetMapping("/listUserPlunder/{userId}/{grade}")
   public void updateStrength(@PathVariable("userId") Long userId, @PathVariable("grade") Integer grade){
      userInfoService.updateStrength(userId,grade);
   }
}
