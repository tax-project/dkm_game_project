package com.dkm.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.httpclient.HttpClientUtils;
import com.dkm.httpclient.HttpResult;
import com.dkm.pay.entity.Pay;
import com.dkm.pay.entity.vo.PayResultVo;
import com.dkm.pay.service.IPayService;
import com.dkm.pay.service.IWxService;
import com.dkm.pay.vilidata.exp.PayTokenUtils;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author qf
 * @date 2020/6/17
 * @vesion 1.0
 **/
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class WxServiceImpl implements IWxService {


   @Autowired
   private PayTokenUtils payTokenUtils;

   @Autowired
   private HttpClientUtils apiService;

   @Value("${pay.url}")
   private String payUrl;

   @Autowired
   private IPayService payService;

   @Autowired
   private IdGenerator idGenerator;

   @Override
   public Object wxPay(String body, String orderNo, Double price) {
      //先获取需要的token信息
      String token = null;
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

      //获取异步回调的接口
      String notifyUrl = payUrl + "/callBack/sucFail";

      String url = payUrl + "/v1/wx/appPay?orderNo=" + orderNo + "&price=" + price + "&body=" + body + "&notifyUrl=" +notifyUrl;

      HttpResult httpResult = apiService.get(url, token);

      if (httpResult.getCode() != 200) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "调用微信支付出错");
      }

      PayResultVo vo = JSONObject.parseObject(httpResult.getBody(),PayResultVo.class);

      if (vo.getCode() != 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, vo.getMsg());
      }

      return JSONObject.parse(httpResult.getBody());
   }
}
