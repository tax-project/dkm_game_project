package com.dkm.friend.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/5/11
 * @vesion 1.0
 **/
@Data
public class FriendNotOnlineVo {

   /**
    * 谁发送的id
    */
   private Long fromId;

   /**
    * 发送给谁
    */
   private Long toId;

   /**
    * 发送内容
    */
   private String content;

   /**
    * 发送时间
    */
   private String createDate;

   /**
    * 3--单聊
    * 101-加好友
    */
   private Integer type;
}
