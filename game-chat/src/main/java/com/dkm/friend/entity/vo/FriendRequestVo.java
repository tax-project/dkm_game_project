package com.dkm.friend.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author qf
 * @date 2020/5/11
 * @vesion 1.0
 **/
@Data
public class FriendRequestVo {

   /**
    * 加人ID
    */
   private Long fromId;

   /**
    * 被加人ID
    */
   private Long toId;

   /**
    * 请求加好友时候的备注
    */
   private String requestRemark;
}
