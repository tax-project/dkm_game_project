package com.dkm.attendant.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/6/5
 * @vesion 1.0
 **/
@Data
public class AttUserAllInfoVo {

   /**
    *跟班主键
    */
   private long aId;

   /**
    *跟班图片
    */
   private String atImg;

   /**
    *跟班名称
    */
   private String atName;

   /**
    *跟班一天生产的时间
    */
   private long exp1;

   /**
    * 被抓人id
    */
   private Long caughtPeopleId;

   /**
    * 0--系统
    * 1--用户
    */
   private Integer sysStatus;
}
