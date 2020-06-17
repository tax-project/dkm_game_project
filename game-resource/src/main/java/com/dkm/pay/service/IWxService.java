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



}
