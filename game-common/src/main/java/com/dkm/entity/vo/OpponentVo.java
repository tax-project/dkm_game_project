package com.dkm.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/7/2
 * @vesion 1.0
 **/
@Data
public class OpponentVo {


   private Long userId;

   /**
    *  头像
    */
   private String imgUrl;

   /**
    * 昵称
    */
   private String nickName;

   /**
    * 等级
    */
   private Integer grade;

   /**
    * 声望
    */
   private Integer userInfoRenown;
}
