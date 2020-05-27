package com.dkm.skill.entity.vo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/5/27
 * @vesion 1.0
 **/
@Data
public class SkillInsertVo {

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
