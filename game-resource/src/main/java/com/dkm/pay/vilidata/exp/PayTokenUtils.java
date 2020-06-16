package com.dkm.pay.vilidata.exp;


import com.alibaba.fastjson.JSONObject;
import com.dkm.httpclient.HttpClientUtils;
import com.dkm.httpclient.HttpResult;
import com.dkm.pay.vilidata.entity.PayToken;
import com.dkm.pay.vilidata.entity.PayVo;
import com.dkm.pay.vilidata.entity.ResultTokenVo;
import com.dkm.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 支付token工具类
 * @author qf
 * @date 2019/11/26
 * @vesion 1.0
 **/
@Slf4j
@Component
public class PayTokenUtils {


   @Autowired
   private HttpClientUtils apiService;

   @Value("${pay.url}")
   private String payUrl;

   @Value("${pay.authUserKey}")
   private String authUserKey;

   @Value("${pay.authPassword}")
   private String authPassword;

   /**
    * 创建容器
    */
   Map<String,Object> map = new ConcurrentHashMap<>();

   /**
    * 请求token
    * @return
    * @throws Exception
    */
   public String getPayToken () throws Exception{

      String url = payUrl +"/v1/auth/authLogin";

      PayToken payToken = new PayToken();
      payToken.setAccessKey(authUserKey);
      payToken.setAccessKeySecret(authPassword);

      String s = JSONObject.toJSONString(payToken);
      HttpResult httpResult = apiService.doPost(url, s);

      PayVo vo = JSONObject.parseObject(httpResult.getBody(), PayVo.class);

      ResultTokenVo result = vo.getResult();

      map.put("pay_token",result.getToken());
      map.put("pay_ext",result.getExpires());
      return result.getToken();
   }


   public String getMapToken() throws Exception {

      String ext = (String) map.get("pay_ext");

      //如果过期时间过期或存的token为空则重新请求，若未过期则用存的token
      if (ext == null) {
         return getPayToken();
      }

      //获取当前时间
      LocalDateTime now = LocalDateTime.now();

      LocalDateTime time = DateUtils.parseDateTime(ext);

      long until = now.until(time, ChronoUnit.DAYS);

      if (until < 0) {
         //未过期
         return (String) map.get("pay_token");
      }

      return getPayToken();
   }

}
