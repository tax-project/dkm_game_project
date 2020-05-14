package com.dkm.wechat.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/5/12
 * @vesion 1.0
 **/
@Data
public class UserLoginVo {

   /**|
    * 用户名
    */
   private String userName;

   /**
    * 密码
    */
   private String password;
}
