package com.dkm.entity.bo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/5/19
 * @vesion 1.0
 **/
@Data
public class UserPlunderBo {


   /**
    * 用户id
    */
   private Long userId;

   /**
    * 用户头像
    */
   private String heardUrl;

   /**
    * 用户名
    */
   private String userName;

   /**
    * 等级
    */
   private Integer grade;
}
