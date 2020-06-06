package com.dkm.attendant.entity.bo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/6/6
 * @vesion 1.0
 **/
@Data
public class CollectResultBo {

   /**
    * 产出表id
    */
   private Long produceId;

   /**
    * 物品id
    */
   private Long goodId;

   /**
    * 物品名称
    */
   private String goodName;

   /**
    * 物品图片地址
    */
   private String goodImg;

   /**
    * 0--金币
    * 2--穿戴物品
    * 3--食物
    */
   private Integer goodType;

   /**
    * 物品数量
    */
   private Integer goodNumber;
}
