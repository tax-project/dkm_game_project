package com.dkm.skill.service;

import com.dkm.skill.entity.vo.MySkillVo;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/22 18:40
 */
public interface ISkillService {
    /**
     * 查询我的技能
     * @return
     */
    List<MySkillVo> queryMySkill();
}
