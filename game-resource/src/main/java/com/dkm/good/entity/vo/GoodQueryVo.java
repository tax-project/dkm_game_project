package com.dkm.good.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/5/19
 * @vesion 1.0
 **/
@Data
public class GoodQueryVo {

   /**
    * 用户id
    */
   private Long userId;

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
   private String goodUrl;

   /**
    * 数量
    */
   private Integer number;
}
