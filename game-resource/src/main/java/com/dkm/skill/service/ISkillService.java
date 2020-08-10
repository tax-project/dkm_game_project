package com.dkm.skill.service;

import com.dkm.skill.entity.UserSkill;
import com.dkm.skill.entity.vo.SkillUserSkillVo;
import com.dkm.skill.entity.vo.SkillVo;
import com.dkm.skill.entity.vo.UserSkillVo;

import java.util.List;
import java.util.Map;

/**
 * @author MQ
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/11 16:17
 */
public interface ISkillService {

    /**
     * 根据用户id查询所有技能
     * @return
     */
    Map<String,Object> queryAllSkillByUserId();

    /**
     * 技能升级
     * @param id
     * @param status
     * @return
     */
    Map<String,Object> upGradeSkills(Long id,Integer status);

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
    void initSkill(Long userId);

}
