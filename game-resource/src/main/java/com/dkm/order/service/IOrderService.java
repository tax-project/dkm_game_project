package com.dkm.order.service;

import com.dkm.order.entity.Order;

import java.util.List;

/**
 * @author qf
 * @date 2020/6/9
 * @vesion 1.0
 **/
public interface IOrderService {

   /**
    *  查询订单信息
    * @param userId 用户id
    * @return 订单详情
    */
   List<Order> queryOrderInfo (Long userId);
}
