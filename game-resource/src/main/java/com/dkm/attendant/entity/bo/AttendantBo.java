package com.dkm.attendant.entity.bo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/6/5
 * @vesion 1.0
 **/
@Data
public class AttendantBo {

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
    * 0--系统
    * 1--用户
    */
   private Integer sysStatus;

   /**
    * 0--未抓
    * 1--已抓
    */
   private Integer status;
}
