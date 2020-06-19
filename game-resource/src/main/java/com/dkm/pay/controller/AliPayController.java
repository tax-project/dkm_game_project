package com.dkm.pay.controller;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.pay.service.IAiliService;
import com.dkm.utils.StringUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * @author qf
 * @date 2020/6/18
 * @vesion 1.0
 **/
@RestController
@RequestMapping("/ali")
public class AliPayController {


   @Autowired
   private IAiliService ailiService;


   @ApiOperation(value = "支付宝支付", notes = "支付宝支付")
   @ApiImplicitParams({
         @ApiImplicitParam(name = "orderNo", value = "订单号", required = true, dataType = "String", paramType = "path"),
         @ApiImplicitParam(name = "price", value = "支付金钱", required = true, dataType = "Double", paramType = "path"),
         @ApiImplicitParam(name = "subject", value = "订单标题", required = true, dataType = "String", paramType = "path"),
         @ApiImplicitParam(name = "body", value = "订单描述", required = true, dataType = "String", paramType = "path"),
         @ApiImplicitParam(name = "returnUrl", value = "前端跳转页面", required = true, dataType = "String", paramType = "path")
   })
   @GetMapping("/payAli")
   @CheckToken
   @CrossOrigin
   public void aliPcPay (@RequestParam("orderNo") String orderNo,
                         @RequestParam("price") Double price,
                         @RequestParam("subject") String subject,
                         @RequestParam("body") String body,
                         @RequestParam("returnUrl") String returnUrl) {
      if (StringUtils.isBlank(orderNo) || price == null || StringUtils.isBlank(subject)
      || StringUtils.isBlank(body) || StringUtils.isBlank(returnUrl)) {
         throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
      }
      ailiService.aliPay(orderNo,price,subject,body,returnUrl);
   }
}
