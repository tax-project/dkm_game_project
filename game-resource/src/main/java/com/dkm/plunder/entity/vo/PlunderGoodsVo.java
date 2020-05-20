package com.dkm.plunder.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/5/19
 * @vesion 1.0
 **/
@Data
public class PlunderGoodsVo {

   /**
    * 主键
    */
   private Long id;

   /**
    * 掠夺id
    */
   private Long plunderId;

   /**
    * 物品id
    */
   private Long goodId;
}
