package com.dkm.userInfo.controller;

import com.dkm.constanct.CodeType;
import com.dkm.entity.bo.UserInfoSkillBo;
import com.dkm.entity.bo.UserPlunderBo;
import com.dkm.entity.vo.*;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.userInfo.entity.bo.IncreaseUserInfoBO;
import com.dkm.userInfo.entity.bo.ReputationRankingBO;
import com.dkm.userInfo.service.IUserInfoService;
import com.dkm.wechat.entity.vo.WeChatUserInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

   @GetMapping("/updateMuch/{much}/{userId}/{userInfoDiamonds}")
   public void updateUserInfo (@PathVariable("much") Integer much,
                               @PathVariable("userId") Long userId,
                               @PathVariable("userInfoDiamonds") Integer userInfoDiamonds) {
      if (userId == null) {
         throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
      }
      userInfoService.updateUserInfo(much,userId,userInfoDiamonds);
   }

   @PostMapping("/increase")
   @CrossOrigin
   public void increaseUserInfo(@RequestBody IncreaseUserInfoBO increaseUserInfoBO){
         userInfoService.increaseUserInfo(increaseUserInfoBO);
   }

   @PostMapping("/cut")
   @CrossOrigin
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

   @PostMapping("/updateInfo")
   public void updateInfo(@RequestBody UserInfoSkillBo bo){
      userInfoService.updateInfo(bo);
   }

   @GetMapping("/listAttUser")
   public List<AttendantWithUserVo> listAttUser(@RequestParam("userId") Long userId){
      return userInfoService.listAttUser(userId);
   }
   @GetMapping("/reputation/ranking")
   public List<ReputationRankingBO> reputationRanking(){
      return userInfoService.reputationRanking();
   }


   @PostMapping("/listOpponent")
   public List<OpponentVo> listOpponent(@RequestBody ListVo listVo){
      return userInfoService.listOpponent(listVo.getList());
   }

   @PostMapping("/queryUserInfoAtt")
   public List<UserInfoAttVo> queryUserInfoAtt(@RequestBody UserAttAllVo listVo){
      return userInfoService.queryUserInfoAtt(listVo.getList());
   }


   @ApiOperation(value = "查询个人信息", notes = "查询个人信息")
   @GetMapping("/queryWeChatUserInfo")
   @CrossOrigin
   @CheckToken
   public WeChatUserInfoVo queryWeChatUserInfo(){
      return userInfoService.queryWeChatUserInfo();
   }
}
