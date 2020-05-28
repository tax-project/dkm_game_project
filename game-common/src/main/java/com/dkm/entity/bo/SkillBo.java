package com.dkm.entity.bo;

import lombok.Data;

/**
 * @author qf
 * @date 2020/5/28
 * @vesion 1.0
 **/
@Data
public class SkillBo {

   /**
    * 技能图片
    */
   private String imgUrl;

   /**
    * 技能等级
    */
   private Integer grade;
}
