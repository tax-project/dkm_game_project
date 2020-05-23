package com.dkm.skill.service;

import com.dkm.skill.entity.vo.MySkillVo;
import com.dkm.skill.entity.vo.UserSkillVo;

import java.util.List;
import java.util.Map;

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

    /**
     * 查看技能详情
     * @param skId
     * @return
     */
    Map<String,Object> querySkillsDetails(long skId);
}
