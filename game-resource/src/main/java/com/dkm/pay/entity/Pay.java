package com.dkm.pay.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.dkm.utils.IdGenerator;
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
@TableName("tb_pay_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Pay extends Model<Pay> {

   /**
    * 主键
    */
   private Long id;

   /**
    *  订单号
    */
   private String orderNo;

   /**
    * 支付流水号
    */
   private String payNo;

   /**
    * 支付金额
    */
   private Double payMoney;

   /**
    * 支付类型 0为微信 1为支付宝
    */
   private Integer payType;

   /**
    *  支付时间
    */
   private LocalDateTime payTime;

   /**
    * 支付结果
    * 0--支付成功
    * 1--支付失败
    */
   private Integer payResult;
}
