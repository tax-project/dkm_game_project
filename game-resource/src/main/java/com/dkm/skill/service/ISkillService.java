package com.dkm.skill.service;

import com.dkm.skill.entity.UserSkill;
import com.dkm.skill.entity.vo.SkillUserSkillVo;
import com.dkm.skill.entity.vo.UserSkillVo;

import java.util.List;
import java.util.Map;

/**
 * @author qf
 * @date 2020/5/27
 * @vesion 1.0
 **/
public interface ISkillService {

    /**
     * 根据用户id查询所有技能
     * @return
     */
    Map<String,Object> queryAllSkillByUserId();

    /**
     * 技能升级
     */
    public Map<String,Object> upgradeSkills(Long id,Integer status);

    /**
     * 提供内部接口 根据用户id查询所有技能
     */
    List<UserSkillVo> querySkillByUserId(Long userId);
}
