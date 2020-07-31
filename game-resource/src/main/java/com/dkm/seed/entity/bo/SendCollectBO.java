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
    * 收取的总金币
    */
   private Integer userGold;
   /**
    * 收取的红包
    */
   private Double userInfoPacketBalance;

   /**
    *  0--正常收取
    *  1--收取种子
    */
   private Integer status;
}
