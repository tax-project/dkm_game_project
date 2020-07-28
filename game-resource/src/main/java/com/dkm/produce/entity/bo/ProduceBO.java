package com.dkm.produce.entity.bo;

import lombok.Data;

/**
 * @auther qf
 * @date 2020/7/28
 * @vesion 1.0
 **/
@Data
public class ProduceBO {

   /**
    *  跟班用户id
    */
   private Long attId;

   /**
    * 次数
    */
   private Integer much;
}
