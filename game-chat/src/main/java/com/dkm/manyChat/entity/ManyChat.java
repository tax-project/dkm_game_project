package com.dkm.manyChat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author qf
 * @date 2020/5/15
 * @vesion 1.0
 **/
@Data
@TableName("tb_many_chat")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ManyChat extends Model<ManyChat> {


   private Long id;

   /**
    * 群聊名字
    */
   private String manyName;

   /**
    * 群聊二维码
    */
   private String manyQrCode;

   /**
    * 公告
    */
   private String manyNotice;

   /**
    * 群备注
    */
   private String manyRemark;

   /**
    * 创建时间
    */
   private LocalDateTime createDate;
}
