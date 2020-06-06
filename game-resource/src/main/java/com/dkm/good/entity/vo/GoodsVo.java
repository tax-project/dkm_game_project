package com.dkm.good.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/5/19
 * @vesion 1.0
 **/
@Data
public class GoodsVo {

   /**
    * 物品名称
    */
   private String name;

   /**
    * 物品图片地址
    */
   private String url;

   /**
    * 物品类别
    */
   private Integer goodType;
}
