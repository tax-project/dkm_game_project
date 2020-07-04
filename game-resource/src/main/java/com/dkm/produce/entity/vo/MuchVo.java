package com.dkm.produce.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/7/4
 * @vesion 1.0
 **/
@Data
public class MuchVo {

   /**
    * 跟班用户id
    */
   private long attUserId;

   /**
    * 跟班id
    */
   private Long attendantId;

   /**
    * 用户id
    */
   private Long userId;

   /**
    * 需要产出的次数
    */
   private Integer attMuch;
}
