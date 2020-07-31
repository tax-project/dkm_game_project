package com.dkm.seed.entity.bo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/7/31
 * @vesion 1.0
 **/
@Data
public class SeedDropBO {

   /**
    * 红包是否掉落
    * 0--不掉落
    * 1--掉落
    */
   private Integer redIsFail;

   /**
    *  红包数量
    */
   private Double redNumber;

   /**
    * 金币是否掉落
    * 0--不掉落
    * 1--掉落
    */
   private Integer goldIsFail;

   /**
    *  金币掉落数量
    */
   private Integer goldNumber;

   /**
    * 掉落花
    * 0--不掉落
    * 1--掉落
    */
   private Integer fallingIsFail;

   /**
    *  花朵掉落数量
    */
   private Integer fallingNumber;
}
