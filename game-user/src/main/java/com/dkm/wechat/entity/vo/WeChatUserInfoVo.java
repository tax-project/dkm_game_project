package com.dkm.wechat.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/7/8
 * @vesion 1.0
 **/
@Data
public class WeChatUserInfoVo {

   /**
    *  id
    */
   private Long userId;

   /**
    * 头像
    */
   private String weChatHeadImgUrl;

   /**
    * 昵称
    */
   private String weChatNickName;

   /**
    * 1--男
    * 2--女
    */
   private Integer userSex;

   /**
    * 等级
    */
   private Integer userInfoGrade;

   /**
    *  声望
    */
   private Integer userInfoRenown;

   /**
    * 年龄
    */
   private Long age;

   /**
    * 用户当前体力
    */
   private Integer userInfoStrength;

   /**
    * 用户总体力
    */
   private Integer userInfoAllStrength;

}
