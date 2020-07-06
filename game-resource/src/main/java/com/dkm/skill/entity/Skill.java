package com.dkm.skill.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author qf
 * @date 2020/5/27
 * @vesion 1.0
 **/
@Data
@TableName("tb_skill")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Skill extends Model<Skill> {

   /**
    * 技能主键id
    */
   private Long id;

   /**
    * 技能名字
    */
   private String skName;

   /**
    * 技能图片
    */
   private String skImg;

   /**
    * 效果一
    */
   private String skEffectOne;

   /**
    * 效果二
    */
   private String skEffectTwo;

}
