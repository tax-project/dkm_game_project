package com.dkm.friend.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author qf
 * @date 2020/5/12
 * @vesion 1.0
 **/
@Data
public class FriendAllListVo {

   /**
    * 好友的用户id
    */
   private Long toId;

   /**
    * 昵称
    */
   private String nickName;

   /**
    * 头像地址
    */
   private String headUrl;
}
