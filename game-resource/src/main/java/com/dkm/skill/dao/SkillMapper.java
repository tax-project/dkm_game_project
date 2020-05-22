package com.dkm.skill.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.skill.entity.Skill;
import com.dkm.skill.entity.Stars;
import com.dkm.skill.entity.UserSkill;
import com.dkm.skill.entity.vo.MySkillVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/22 18:45
 */
@Component
public interface SkillMapper extends BaseMapper<Skill> {
    /**
     * 查询我的技能
     * @param userId
     * @return
     */
    List<MySkillVo> queryMySkill(Long userId);
    /**
     * 初始化值
     */
    int addSkill(List<UserSkill> list);

    /**
     * 初始星星值
     */
    int addStarts(List<Stars> starsList);

}
