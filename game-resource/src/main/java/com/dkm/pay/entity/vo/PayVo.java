package com.dkm.pay.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author qf
 * @date 2020/6/17
 * @vesion 1.0
 **/
@Data
public class PayVo {

   /**
    *  订单号
    */
   private String orderNo;

   /**
    * 支付流水号
    */
   private String payNo;

   /**
    * 支付金额
    */
   private Double payMoney;

   /**
    * 支付类型 0为微信 1为支付宝
    */
   private Integer payType;

   /**
    *  支付时间
    */
   private LocalDateTime payTime;

   /**
    * 支付结果
    * 0--支付成功
    * 1--支付失败
    */
   private Integer payResult;
}
