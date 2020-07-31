package com.dkm.seed.entity.bo;

import lombok.Data;

/**
 * 种植
 * @author qf
 * @date 2020/7/30
 * @vesion 1.0
 **/
@Data
public class SendPlantBO {

   /**
    * 种子id
    */
   private Long seedId;
   /**
    * 等级
    */
   private Integer seedGrade;
   /**
    * 种子种植金币
    */
   private Integer seedGold;

   /**
    *  种植的个数
    */
   private Integer landNumber;
}
