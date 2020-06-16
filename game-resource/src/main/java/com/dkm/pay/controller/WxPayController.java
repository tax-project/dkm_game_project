package com.dkm.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.httpclient.HttpClientUtils;
import com.dkm.httpclient.HttpResult;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.pay.entity.vo.PayResultVo;
import com.dkm.pay.vilidata.exp.PayTokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author qf
 * @date 2020/6/15
 * @vesion 1.0
 **/
@RestController
@Slf4j
@Api(tags = "微信支付API")
@RequestMapping("/v1/wx")
public class WxPayController {

   @Autowired
   private PayTokenUtils payTokenUtils;

   @Autowired
   private HttpClientUtils apiService;

   @Value("${pay.url}")
   private String payUrl;


   @ApiOperation(value = "微信App支付", notes = "微信App支付")
   @ApiImplicitParams({
         @ApiImplicitParam(name = "orderNo", value = "订单号", required = true, dataType = "String", paramType = "path"),
         @ApiImplicitParam(name = "price", value = "支付金钱", required = true, dataType = "Double", paramType = "path"),
         @ApiImplicitParam(name = "body", value = "订单描述", required = true, dataType = "String", paramType = "path"),
   })
   @GetMapping("/wxAppPay")
   @CheckToken
   @CrossOrigin
   public Object orders(@RequestParam("body") String body,
                                     @RequestParam("orderNo") String orderNo,
                                     @RequestParam("price") Double price) {
      //先获取需要的token信息
      String token = null;
      try {
         token = payTokenUtils.getPayToken();
      } catch (Exception e) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "获取支付token异常");
      }

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
