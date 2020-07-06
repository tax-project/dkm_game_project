package com.dkm.friend.entity.vo;

import lombok.Data;


/**
 * @author qf
 * @date 2020/5/11
 * @vesion 1.0
 **/
@Data
public class FriendRequestInfoVo {

   private Long id;

   /**
    * 加入人id
    */
   private Long fromId;

   /**
    *  头像
    */
   private String heardUrl;

   /**
    * 加人ID昵称
    */
   private String fromNick;

   /**
    * 创建时间
    */
   private String requestTime;

   /**
    * 0--申请中
    * 1--同意
    * 2--拒绝
    */
   private Integer status;

   /**
    * 请求加好友时候的备注
    */
   private String requestRemark;
}
