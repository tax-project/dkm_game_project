package com.dkm.wechat.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/7/3
 * @vesion 1.0
 **/
@Data
public class UserChatInfoVo {

   /**
    *  id
    */
   private Long userId;

   /**
    * 微信昵称
    */
   private String weChatNickName;
   /**
    * 微信头像地址
    */
   private String weChatHeadImgUrl;

   /**
    * 性别
    * 1--男
    * 2--女
    */
   private Integer userSex;

   /**
    * 个人二维码
    */
   private String qrCode;

   /**
    *  用户地区
    *  国家-省-市
    */
   private String userAddress;
}
