package com.dkm.problem.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/5/12
 * @vesion 1.0
 **/
@Data
public class HandOutRedEnvelopesVo {

   /**
    * 声望
    */
   private Integer renown;

   /**
    * 经验
    */
   private Integer experience;

   /**
    * 金主礼包
    */
   private BagVo bagVo;

   /**
    * 增加的参与红包活动次数
    */
   private Integer redMuch;
}
