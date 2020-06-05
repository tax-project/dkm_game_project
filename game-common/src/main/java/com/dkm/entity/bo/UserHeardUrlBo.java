package com.dkm.entity.bo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/6/5
 * @vesion 1.0
 **/
@Data
public class UserHeardUrlBo {


   private Long userId;

   /**
    * 用户头像地址
    */
   private String headUrl;

   /**
    * 名称
    */
   private String nickName;
}
