package com.dkm.problem.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/5/25
 * @vesion 1.0
 **/
@Data
public class RedBagVo {

   /**
    * 今日已抢次数
    */
   private Integer redEnvelopesNumber;

   /**
    * 今日上限次数
    */
   private Integer allRedEnvelopesNumber;
}
