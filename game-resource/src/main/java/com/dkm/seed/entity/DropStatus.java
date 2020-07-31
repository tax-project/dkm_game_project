package com.dkm.seed.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author qf
 * @date 2020/7/31
 * @vesion 1.0
 **/
@Data
@TableName("tb_drop_status")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class DropStatus extends Model<DropStatus> {

   /**
    *  id
    */
   private Long id;

   /**
    * 用户id
    */
   private Long useId;

   /**
    * 结束时间
    */
   private LocalDateTime endTime;

   /**
    *  种植掉落次数
    */
   private Integer muchNumber;
}
