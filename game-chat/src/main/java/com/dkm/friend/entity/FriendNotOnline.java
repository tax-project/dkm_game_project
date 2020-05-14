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
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("tb_friend_online")
public class FriendNotOnline extends Model<FriendNotOnline> {

   private Long id;

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
   private LocalDateTime createDate;

   /**
    * 1--连接请求
    * 2--心跳消息
    * 3--单聊消息
    * 4--群聊信息
    *
    *
    *
    * 100--强制下线
    * 101-申请添加好友(未在线消息和添加好友都以单聊消息形式发送,则type==3)
    */
   private Integer type;

   /**
    * 是否查看
    * 0--未查看
    * 1--已查看
    */
   private Integer isLook;
}
