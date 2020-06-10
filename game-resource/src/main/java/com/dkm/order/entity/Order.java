package com.dkm.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author qf
 * @date 2020/6/9
 * @vesion 1.0
 **/
@Data
@TableName("tb_order")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Order extends Model<Order> {

   /**
    * 订单id
    */
   private Long id;

   /**
    * 用户id
    */
   private Long userId;

   /**
    * 订单号
    */
   private String orderNo;

   /**
    * 支付金额
    */
   private Double payMoney;

   /**
    *  0--管家
    *  1--首冲
    *  2--
    *  3--
    *  4--
    */
   private Integer orderType;

   /**
    * 下单时间
    */
   private LocalDateTime orderDate;

   /**
    * 0--待付款
    * 1--已取消
    * 2--已付款
    */
   private Integer orderStatus;
}
