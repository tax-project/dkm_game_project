package com.dkm.friend.entity.bo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/6/11
 * @vesion 1.0
 **/
@Data
public class FriendAllQueryBo {

   /**
    * 谁的账号
    */
   private Long fromId;

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

   /**
    * 性别
    * 1--男
    * 2--女
    */
   private Integer userSex;
}
