package com.dkm.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/7/10
 * @vesion 1.0
 **/
@Data
public class UserInfoAttVo {

   /**
    *  用户id
    */
   private Long userId;

   /**
    * 头像
    */
   private String heardUrl;

   /**
    * 昵称
    */
   private String nickName;
}
