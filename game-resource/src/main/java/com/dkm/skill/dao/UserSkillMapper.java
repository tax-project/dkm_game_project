package com.dkm.skill.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.skill.entity.UserSkill;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/27
 * @vesion 1.0
 **/
@Component
public interface UserSkillMapper extends IBaseMapper<UserSkill> {

    /**
     * 批量增加用户技能
     * @param list
     * @return
     */
    int addUserSkill(List<UserSkill> list);
}
