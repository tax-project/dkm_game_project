package com.dkm.skill.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/5/27
 * @vesion 1.0
 **/
@Data
public class UserSkillResultVo {

   /**
    * 技能名字
    */
   private String skName;

   /**
    * 技能图片
    */
   private String skImg;

   private Long id;

   /**
    * 等级
    */
   private Integer skGrade;

   /**
    * 当前技能升级成功率
    */
   private String skCurrentSuccessRate;

   /**
    * 当前声望累计达到总声望
    */
   private Integer skAllPrestige;

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
