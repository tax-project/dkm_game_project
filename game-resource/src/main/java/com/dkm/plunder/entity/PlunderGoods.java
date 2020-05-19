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
@TableName("tb_plunder_good")
public class PlunderGoods extends Model<PlunderGoods> {

   /**
    * 主键
    */
   private Long id;

   /**
    * 掠夺id
    */
   private Long plunderId;

   /**
    * 物品id
    */
   private Long goodId;
}
