package com.dkm.pay.controller;


import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.islogin.CheckToken;
import com.dkm.pay.service.IWxService;
import com.dkm.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
   private IWxService wxService;

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

      if (StringUtils.isBlank(body) || StringUtils.isBlank(orderNo) || price == null) {
         throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
      }
      return wxService.wxPay(body,orderNo,price);
   }



   @ApiOperation(value = "提现到零钱(最低不能少于0.3元)", notes = "提现到零钱(最低不能少于0.3元)")
   @ApiImplicitParams({
         @ApiImplicitParam(name = "openId", value = "微信openId", required = true, dataType = "String", paramType = "path"),
         @ApiImplicitParam(name = "price", value = "支付金钱", required = true, dataType = "Double", paramType = "path"),
   })
   @GetMapping("/toWxPerson")
   @CheckToken
   @CrossOrigin
   public Object toPerson(@RequestParam("openId") String openId,
                        @RequestParam("price") Double price) {

      if (StringUtils.isBlank(openId) || price == null) {
         throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
      }
      return wxService.toPerson(openId,price);
   }




}
