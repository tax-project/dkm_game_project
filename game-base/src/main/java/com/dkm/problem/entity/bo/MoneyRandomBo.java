package com.dkm.problem.entity.bo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/6/6
 * @vesion 1.0
 **/
@Data
public class MoneyRandomBo {

   private Long id;

   /**
    * 用户id
    * 谁发红包
    */
   private Long userId;

   /**
    * （红包金额）
    * 钻石数量
    */
   private Integer diamonds;
}
