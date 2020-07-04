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
@TableName("tb_user_skill")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserSkill extends Model<UserSkill> {

   /**
    * 主键
    */
   private Long id;

   /**
    * 用户id
    */
   private Long userId;

   /**
    * 技能id
    */
   private Long skId;

   /**
    * 等级
    */
   private Integer skGrade;


   /**
    * 当前技能升级成功率
    */
   private Integer skCurrentSuccessRate;

   /**
    * 熟练度
    */
   private Integer skDegreeProficiency;

   /**
    * 升级一级增加的声望
    */
   private Integer skAddPrestige;

   /**
    * 消耗的总个数
    */
   private Integer skAllConsume;

   /**
    * 当前使用消耗的个数
    */
   private Integer skCurrentConsume;
}
