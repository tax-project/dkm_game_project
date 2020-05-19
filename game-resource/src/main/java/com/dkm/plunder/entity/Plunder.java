package com.dkm.plunder.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author qf
 * @date 2020/5/19
 * @vesion 1.0
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("tb_plunder")
public class Plunder extends Model<Plunder> {

   /**
    * 主键
    */
   private Long id;

   /**
    * 谁抢夺
    * 用户id
    */
   private Long fromId;

   /**|
    * 抢夺谁
    * 用户id
    */
   private Long toId;


}
