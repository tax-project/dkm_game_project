package com.dkm.attendant.entity.bo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/6/6
 * @vesion 1.0
 **/
@Data
public class AttPutBo {

   /**
    * 数量
    */
   private Integer number;

   /**
    * 物品ID
    */
   private Long goodId;

   /**
    * 物品名字
    */
   private String goodName;

   /**
    * 物品图片
    */
   private String imgUrl;

   /**
    *  跟班id
    */
   private Long attendantId;

   /**
    *  被抓人id
    */
   private Long caughtPeopleId;
}
