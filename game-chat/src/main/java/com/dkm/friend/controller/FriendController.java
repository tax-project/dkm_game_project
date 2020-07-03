package com.dkm.friend.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.friend.entity.bo.FriendBo;
import com.dkm.friend.entity.vo.FriendAllListVo;
import com.dkm.friend.entity.vo.ResultVo;
import com.dkm.friend.service.IFriendService;
import com.dkm.jwt.islogin.CheckToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/12
 * @vesion 1.0
 **/
@Slf4j
@Api(tags = "好友Api")
@RestController
@RequestMapping("/v1/friend")
public class FriendController {


   @Autowired
   private IFriendService friendService;


   @ApiOperation(value = "展示全部好友", notes = "展示全部好友")
   @GetMapping("/listAllFriend")
   @CrossOrigin
   @CheckToken
   public List<FriendAllListVo> listAllFriend () {
      return friendService.listAllFriend();
   }


   @ApiOperation(value = "删除好友", notes = "删除好友")
   @ApiImplicitParam(name = "toId", value = "要删除的人的id", required = true, dataType = "Long", paramType = "path")
   @GetMapping("/deleteFriend")
   @CrossOrigin
   @CheckToken
   public ResultVo deleteFriend (@RequestParam("toId") Long toId) {

      if (toId == null) {
         throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
      }

      friendService.deleteFriend(toId);

      ResultVo resultVo = new ResultVo();
      resultVo.setResult("ok");

      return resultVo;

   }


   @ApiOperation(value = "可能认识的人", notes = "可能认识的人")
   @GetMapping("/listAllProblemFriend")
   @CrossOrigin
   @CheckToken
   public List<FriendBo> listAllProblemFriend () {
      return friendService.listAllProblemFriend();
   }


   @ApiOperation(value = "触发离线消息(当连上ws时候调用)", notes = "触发离线消息(当连上ws时候调用)")
   @GetMapping("/getOntOnlineInfo")
   @CrossOrigin
   @CheckToken
   public void getOntOnlineInfo () {
      friendService.getOntOnlineInfo();
   }
}
