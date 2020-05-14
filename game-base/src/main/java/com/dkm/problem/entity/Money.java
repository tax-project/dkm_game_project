package com.dkm.problem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author qf
 * @date 2020/5/9
 * @vesion 1.0
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("tb_money")
public class Money extends Model<Money> {


   private Long id;

   /**
    * 用户id
    * 谁发红包
    */
   private Long userId;

   /**
    * （红包金额）
    * 钻石数量
    */
   private Integer diamonds;

   /**
    * 设定红包领取人数
    */
   private Integer number;

   /**
    * 红包答题人数
    */
   private Integer inNumber;

   /**
    * 一道题的钻石（金额）
    */
   private Integer oneDiamonds;

   /**
    * 状态
    * 0--未回答
    * 1--正在回答
    * 2--已结束
    */
   private Integer status;

   /**
    * 创建时间
    */
   private LocalDateTime createDate;
}
