package com.dkm.pay.service.impl;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.httpclient.HttpClientUtils;
import com.dkm.httpclient.HttpResult;
import com.dkm.pay.entity.Pay;
import com.dkm.pay.service.IAiliService;
import com.dkm.pay.service.IPayService;
import com.dkm.pay.vilidata.exp.PayTokenUtils;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author qf
 * @date 2020/6/18
 * @vesion 1.0
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AliServiceImpl implements IAiliService {

   @Autowired
   private PayTokenUtils payTokenUtils;

   @Autowired
   private HttpClientUtils apiService;

   @Value("${pay.url}")
   private String payUrl;

   /**
    *  支付模块的地址
    */
   @Value("${pay.payUrl}")
   private String url;

   @Autowired
   private IPayService payService;

   @Autowired
   private IdGenerator idGenerator;

   /**
    *  支付宝支付
    * @param orderNo 订单号
    * @param price 价格
    * @param subject 订单标题
    * @param body 订单描述
    * @param returnUrl 前端跳转页面
    */
   @Override
   public void aliPay(String orderNo, Double price, String subject, String body, String returnUrl) {
      //后端回调接口
      String notifyUrl = payUrl + "/callBack/sucAliFail";

      //先获取需要的token信息
      String token;
      try {
         token = payTokenUtils.getPayToken();
      } catch (Exception e) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "获取支付token异常");
      }


      //增加支付信息
      Pay pay = new Pay();
      pay.setId(idGenerator.getNumberId());
      pay.setPayMoney(price);
      pay.setOrderNo(orderNo);
      payService.insertPay(pay);

      //获取请求支付模块的地址
      String requestUrl = url + "/v1/ali/payAli?orderNo=" + orderNo + "&price=" + price +
            "&subject=" + subject + "&body=" + body + "&returnUrl=" + returnUrl + "&notifyUrl=" + notifyUrl;


      HttpResult httpResult = apiService.get(requestUrl, token);

      if (httpResult.getCode() != 200) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "调用支付宝支付出错");
      }

   }
}
