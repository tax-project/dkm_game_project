package com.dkm.manyChat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author qf
 * @date 2020/5/15
 * @vesion 1.0
 **/
@Data
@TableName("tb_many_chat_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ManyChatInfo extends Model<ManyChatInfo> {

   private Long id;

   /**
    *  群聊表id
    */
   private Long manyChatId;

   /**
    * 用户id
    */
   private Long userId;


   /**
    * 昵称
    */
   private String manyNickName;

   /**
    * 每个用户在群里的备注
    */
   private String manyNickRemark;
}
