package com.dkm.friend.entity.bo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/6/11
 * @vesion 1.0
 **/
@Data
public class FriendBo {

   /**
    * 好友的用户id
    */
   private Long toId;

   /**
    * 头像
    */
   private String headUrl;

   /**
    * 名称
    */
   private String nickName;

   /**
    * 性别
    * 1--男
    * 2--女
    */
   private Integer userSex;

   /**
    * 共同好友
    */
   private FriendToWithBo bo;
}
