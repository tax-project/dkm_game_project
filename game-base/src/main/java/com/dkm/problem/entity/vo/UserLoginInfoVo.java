package com.dkm.problem.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/7/3
 * @vesion 1.0
 **/
@Data
public class UserLoginInfoVo {

   private Long userId;

   /**
    * 登录人昵称
    */
   private String nickName;

   /**
    * 头像
    */
   private String heardUrl;


}
