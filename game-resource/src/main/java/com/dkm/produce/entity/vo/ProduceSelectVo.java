package com.dkm.produce.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/6/7
 * @vesion 1.0
 **/
@Data
public class ProduceSelectVo {

   /**
    * 产出表id
    */
   private Long produceId;

   /**
    * 用户产出表id
    */
   private Long produceUserId;
}
