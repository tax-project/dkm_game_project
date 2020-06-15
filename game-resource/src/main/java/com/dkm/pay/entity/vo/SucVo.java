package com.dkm.pay.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/6/15
 * @vesion 1.0
 **/
@Data
public class SucVo {

   /**
    *  成功---SUCCESS
    *  失败---FAIL
    */
   private String msg;

   /**
    * 订单号
    */
   private String orderNo;

   /**
    * 第三方流水号
    */
   private String tradeNo;

}
