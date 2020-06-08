package com.dkm.wechat.entity.bo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/6/8
 * @vesion 1.0
 **/
@Data
public class UserDataBO {

   /**
    *  头像地址
    */
   private String heardUrl;

   /**
    * 昵称
    */
   private String nickName;

   /**
    * 年龄日期 例:2020-06-08
    */
   private String userAge;

   /**
    * 性别
    * 1--男
    * 2--女
    */
   private Integer userSex;

   /**
    * 个性签名
    */
   private String userSign;

   /**
    * 个人说明
    */
   private String userExplain;
}
