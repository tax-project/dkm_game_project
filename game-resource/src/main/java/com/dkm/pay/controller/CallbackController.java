package com.dkm.pay.controller;

import com.dkm.pay.entity.Pay;
import com.dkm.pay.entity.vo.SucVo;
import com.dkm.pay.service.IPayService;
import com.dkm.utils.IdGenerator;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author qf
 * @date 2020/6/15
 * @vesion 1.0
 **/
@Slf4j
@Api(tags = "微信，支付宝回调接口")
@RestController
@RequestMapping("/callBack")
public class CallbackController {


   @Autowired
   private IPayService payService;

   @Autowired
   private IdGenerator idGenerator;

   /**
    * 微信回调接口
    * @param vo 支付结果的参数
    */
   @PostMapping("/sucFail")
   @CrossOrigin
   public void sucFail (@RequestBody SucVo vo) {

      Pay pay = new Pay();

      //支付金额

      pay.setId(idGenerator.getNumberId());
      pay.setOrderNo(vo.getOrderNo());
      pay.setPayNo(vo.getTradeNo());
      if ("SUCCESS".equals(vo.getMsg())) {
         //支付成功
         pay.setPayResult(0);
      } else {
         pay.setPayResult(1);
      }
      pay.setPayTime(LocalDateTime.now());
      pay.setPayType(0);

      payService.insertPay(pay);
   }


   /**
    * 支付宝回调接口
    * @param vo 支付结果的参数
    */
   @PostMapping("/sucAliFail")
   @CrossOrigin
   public void sucAliFail (@RequestBody SucVo vo) {

      Pay pay = new Pay();
      pay.setId(idGenerator.getNumberId());
      pay.setOrderNo(vo.getOrderNo());
      pay.setPayNo(vo.getTradeNo());
      if ("SUCCESS".equals(vo.getMsg())) {
         //支付成功
         pay.setPayResult(0);
      } else {
         pay.setPayResult(1);
      }
      pay.setPayTime(LocalDateTime.now());
      pay.setPayType(1);

      //支付金额

      payService.insertPay(pay);

   }
}
