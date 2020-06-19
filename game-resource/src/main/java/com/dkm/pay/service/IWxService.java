package com.dkm.pay.service;


/**
 * @author qf
 * @date 2020/6/17
 * @vesion 1.0
 **/
public interface IWxService {


   /**
    *  微信APP支付
    * @param body
    * @param orderNo
    * @param price
    * @return
    */
   Object wxPay(String body, String orderNo, Double price);


   /**
    *  提现
    * @param openId 微信openId
    * @param price 金钱
    * @return 返回支付结果
    */
   Object toPerson (String openId, Double price);

}
