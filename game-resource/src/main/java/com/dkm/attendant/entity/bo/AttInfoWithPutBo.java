package com.dkm.attendant.entity.bo;

import com.dkm.produce.entity.vo.AttendantPutVo;
import lombok.Data;

import java.util.List;

/**
 * @author qf
 * @date 2020/6/6
 * @vesion 1.0
 **/
@Data
public class AttInfoWithPutBo {

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
   private String expTime;

   /**
    * 被抓人id
    */
   private Long caughtPeopleId;

   /**
    * 0--系统
    * 1--用户
    */
   private Integer sysStatus;

   /**
    *  跟班用户Id
    */
   private Long attUserId;

   /**
    *  该跟班产出的物品类表
    */
   private List<AttendantPutVo> putList;
}
