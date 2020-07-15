package com.dkm.attendant.entity.bo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/7/15
 * @vesion 1.0
 **/
@Data
public class AttUserInfoBo {

   /**
    * 用户id
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
    * 声望
    */
   private String userInfoRenown;

   /**
    * 0--没有主人
    * 1--有主人
    */
   private Integer status;
}
