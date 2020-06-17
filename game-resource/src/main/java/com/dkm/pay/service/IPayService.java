package com.dkm.pay.service;


import com.dkm.pay.entity.Pay;
import com.dkm.pay.entity.vo.PayInfoVo;

import java.util.List;

/**
 * @author qf
 * @date 2020/6/9
 * @vesion 1.0
 **/
public interface IPayService {

   /**
    *  查询所有支付记录
    * @param userId 用户id
    * @return 返回支付记录
    */
   List<PayInfoVo> listAllPayInfo (Long userId);

   /**
    *  增加支付信息
    * @param pay 支付返回的结果
    */
   void insertPay (Pay pay);

   /**
    *  根据订单号修改支付信息
    * @param pay
    */
   void updatePayInfo (Pay pay);

}
