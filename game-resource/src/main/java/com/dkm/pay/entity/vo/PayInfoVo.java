package com.dkm.pay.entity.vo;

import lombok.Data;


/**
 * @author qf
 * @date 2020/6/9
 * @vesion 1.0
 **/
@Data
public class PayInfoVo {


   /**
    * 主键
    */
   private Long id;

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
   private String payTime;

   /**
    * 用户id
    */
   private Long userId;

   /**
    *  0--管家
    *  1--首冲
    *  2--
    *  3--
    *  4--
    */
   private Integer orderType;

   /**
    * 0--待付款
    * 1--已取消
    * 2--已付款
    */
   private Integer orderStatus;
}
