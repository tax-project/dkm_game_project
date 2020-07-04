package com.dkm.problem.entity.bo;

import lombok.Data;



/**
 * @author qf
 * @date 2020/6/5
 * @vesion 1.0
 **/
@Data
public class MoneyBo {

   private Long id;

   /**
    * 用户id
    * 谁发红包
    */
   private Long userId;

   /**
    * 用户昵称
    */
   private String userNickName;

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
    * 用户头像地址
    */
   private String headUrl;
}
