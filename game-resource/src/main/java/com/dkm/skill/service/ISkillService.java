package com.dkm.skill.service;

import com.dkm.skill.entity.UserSkill;
import com.dkm.skill.entity.vo.SkillUserSkillVo;
import com.dkm.skill.entity.vo.SkillVo;
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

    /**
     * 内部使用
     * 查询技能的图片和等级
     */
    List<SkillVo> queryAllSkillByUserIdImgGrade(Long userId);

    /**
     * 提供给个人中心那边，进入个人中心页面初始化技能
     * 初始化技能
     * @return
     */
    int initSkill(Long userId);

}
