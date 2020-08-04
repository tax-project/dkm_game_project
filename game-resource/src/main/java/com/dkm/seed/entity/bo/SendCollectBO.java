package com.dkm.seed.entity.bo;

import lombok.Data;

/**
 * 收取
 * @author qf
 * @date 2020/7/30
 * @vesion 1.0
 **/
@Data
public class SendCollectBO {

   /**
    * 等级
    */
   private Integer seedGrade;

   /**
    *  0--正常收取
    *  1--收取种子
    */
   private Integer status;

   /**
    * 0--我自己收
    * 1--别人抢
    */
   private Integer seedMeOrOther;

   /**
    *  抢谁的金币-->那个人的用户id
    */
   private Long userId;
}
