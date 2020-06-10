package com.dkm.friend.controller;

import com.dkm.friend.entity.vo.FriendNotOnlineVo;
import com.dkm.friend.service.IFriendNotOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/14
 * @vesion 1.0
 **/
@RestController
@RequestMapping("/v1/notOnline")
public class FriendNotOnlineController {

   @Autowired
   private IFriendNotOnlineService friendNotOnlineService;

   @GetMapping("/queryNotInfo/{userId}")
   public List<FriendNotOnlineVo> queryNotOnline(@PathVariable("userId") Long userId) {
      return friendNotOnlineService.queryOne(userId);
   }


   @PostMapping("/deleteLookStatus")
   public void deleteLookStatus(@RequestBody List<Long> list) {
      friendNotOnlineService.deleteLook(list);
   }
}
