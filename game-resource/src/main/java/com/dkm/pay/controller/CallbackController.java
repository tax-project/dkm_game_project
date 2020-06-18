package com.dkm.pay.controller;

import com.dkm.pay.entity.Pay;
import com.dkm.pay.entity.vo.AliPayParamVo;
import com.dkm.pay.entity.vo.PayVo;
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
@RestController
@RequestMapping("/callBack")
public class CallbackController {


   @Autowired
   private IPayService payService;

   /**
    * 微信回调接口
    * @param vo 支付结果的参数
    */
   @PostMapping("/sucFail")
   @CrossOrigin
   public void sucFail (@RequestBody SucVo vo) {


      PayVo payVo = new PayVo();
      payVo.setOrderNo(vo.getOrderNo());
      payVo.setPayNo(vo.getTradeNo());
      payVo.setPayTime(LocalDateTime.now());
      payVo.setPayType(0);
      if ("SUCCESS".equals(vo.getMsg())) {
         //支付成功
         payVo.setPayResult(0);
      } else {
         payVo.setPayResult(1);
      }

      payService.updatePayInfo(payVo);
   }


   /**
    * 支付宝回调接口
    * @param vo 支付结果的参数
    */
   @PostMapping("/sucAliFail")
   @CrossOrigin
   public void sucAliFail (@RequestBody AliPayParamVo vo) {

      PayVo payVo = new PayVo();
      payVo.setOrderNo(vo.getOrderNo());
      payVo.setPayNo(vo.getTradeNo());
      payVo.setPayTime(LocalDateTime.now());
      payVo.setPayType(1);
      if ("SUCCESS".equals(vo.getMsg())) {
         //支付成功
         payVo.setPayResult(0);
      } else {
         payVo.setPayResult(1);
      }

      payService.updatePayInfo(payVo);

   }
}
