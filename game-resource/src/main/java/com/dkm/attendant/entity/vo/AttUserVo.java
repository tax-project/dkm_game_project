package com.dkm.attendant.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/6/5
 * @vesion 1.0
 **/
@Data
public class AttUserVo {

   /**
    * 时间戳
    */
   private String s;

   /**
    * 跟班主人id
    */
   private Long caughtPeopleId;

   /**
    * 跟班id
    */
   private Long aId;

   /**
    * 0--抢跟班成功
    * 1--该用户被别人抢走了,打他主人,打过了就可以
    */
   private Integer status;
}
