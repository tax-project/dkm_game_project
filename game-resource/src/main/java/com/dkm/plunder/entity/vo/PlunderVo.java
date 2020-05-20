package com.dkm.plunder.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/5/19
 * @vesion 1.0
 **/
@Data
public class PlunderVo {

   /**
    * 被抢人的用户id
    */
   private Long userId;

   /**
    * 物品id
    */
   private Long goodsId;

   /**
    * 被抢人的等级
    */
   private Integer grade;
}
