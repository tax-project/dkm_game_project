package com.dkm.entity.bo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/5/27
 * @vesion 1.0
 **/
@Data
public class UserInfoSkillBo {

   /**
    * 减少的金币
    */
   private Integer gold;

   /**
    * 增加的声望
    */
   private Integer prestige;

   /**
    * 钻石
    */
   private Integer diamonds;

   /**
    * 用户id
    */
   private Long userId;
}
