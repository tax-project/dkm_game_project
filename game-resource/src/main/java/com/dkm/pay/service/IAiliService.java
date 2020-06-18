package com.dkm.pay.service;

/**
 * @author qf
 * @date 2020/6/18
 * @vesion 1.0
 **/
public interface IAiliService {


   /**
    *  支付宝支付
    * @param orderNo 订单号
    * @param price 价格
    * @param subject 订单标题
    * @param body 订单描述
    * @param returnUrl 前端跳转页面
    */
   void aliPay (String orderNo, Double price, String subject, String body, String returnUrl);
}
