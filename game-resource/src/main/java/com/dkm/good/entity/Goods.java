package com.dkm.good.entity;

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
@TableName("tb_goods")
public class Goods extends Model<Goods> {

   private Long id;

   /**
    * 物品名称
    */
   private String name;

   /**
    * 物品图片地址
    */
   private String url;

   /**
    *  物品类别
    *  0--金币
    *  2--道具
    *  3--食物
    */
   private Integer goodType;
}
