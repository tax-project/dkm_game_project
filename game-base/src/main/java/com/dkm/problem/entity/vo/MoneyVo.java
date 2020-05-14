package com.dkm.problem.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/5/9
 * @vesion 1.0
 **/
@Data
public class MoneyVo {


   /**
    * （红包金额）
    * 钻石数量
    */
   private Integer diamonds;

   /**
    * 红包领取人数
    */
   private Integer number;
}
