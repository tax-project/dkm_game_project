package com.dkm.friend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author qf
 * @date 2020/5/11
 * @vesion 1.0
 **/
@Data
@TableName("tb_friend_request")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class FriendRequest extends Model<FriendRequest> {

   private Long id;

   /**
    * 加人ID
    */
   private Long fromId;

   /**
    * 被加人ID
    */
   private Long toId;

   /**
    * 创建时间
    */
   private LocalDateTime requestTime;

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
