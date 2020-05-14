package com.dkm.problem.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/5/10
 * @vesion 1.0
 **/
@Data
public class ScoreListVo {

   /**
    * 用户名，微信昵称
    */
   private String userName;

   /**
    * 收的红包金额
    */
   private Double price;

   /**
    * 操作时间
    */
   private String date;
}
