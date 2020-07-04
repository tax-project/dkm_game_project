package com.dkm.skill.service;

import com.dkm.entity.bo.SkillBo;
import com.dkm.meskill.entity.Skill;
import com.dkm.skill.entity.vo.SkillInsertVo;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/27
 * @vesion 1.0
 **/
public interface ISkillService {

   /**
    *  系统增加技能
    * @param vo 技能的参数
    */
   void insertSkill (SkillInsertVo vo);

   /**
    * 根据Id查询
    * @param skillId
    * @return
    */
   Skill queryById (Long skillId);

   /**
    *  展示所有技能
    * @return 返回结果
    */
   List<Skill> listAllSkill ();

   /**
    *  根据用户id查询所有技能信息
    * @param userId 用户Id
    * @return 返回技能图片 和等级
    */
   List<SkillBo> queryAllSkillByUserId (Long userId);

}
