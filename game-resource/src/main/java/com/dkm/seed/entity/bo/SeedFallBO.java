package com.dkm.seed.entity.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qf
 * @date 2020/8/4
 * @vesion 1.0
 **/
@Data
public class SeedFallBO implements Serializable {

   /**
    * 红包
    */
   private Double redPacketsDropped;

   /**
    * 金币
    */
   private Integer dropped;

   /**
    * 花
    */
   private Integer fallingNumber;
}
