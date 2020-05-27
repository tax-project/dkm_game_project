package com.dkm.skill.service;

import com.dkm.skill.entity.vo.UserSkillResultVo;
import com.dkm.skill.entity.vo.UserSkillUpGradeVo;
import com.dkm.skill.entity.vo.UserSkillVo;

/**
 * @author qf
 * @date 2020/5/27
 * @vesion 1.0
 **/
public interface IUserSkillService {

   /**
    * 初始化技能
    * @param vo 技能Id
    */
   void insertUserSkill (UserSkillVo vo);

   /**
    * 升级
    * @param vo 技能id
    */
   void upGrade (UserSkillUpGradeVo vo);

   /**
    *  点击消耗增加技能升级成功率
    * @param id
    */
   void consume (Long id);

   /**
    *  展示技能详情
    * @param skillId 技能id
    * @return 返回展示结果
    */
   UserSkillResultVo getSkillResult (Long skillId);
}
