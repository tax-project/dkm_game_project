package com.dkm.skill.service;

import com.dkm.skill.entity.UserSkill;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/27
 * @vesion 1.0
 **/
public interface IUserSkillService {

    /**
     * 批量增加用户技能
     * @param list
     * @return
     */
    int addUserSkill(List<UserSkill> list);

    /**
     * 升级技能
     * 根据技能id查询数据
     */
    UserSkill querySkillById(Long id);

    /**
     * 修改信息
     */
    int updateUserSkill(Long id,UserSkill userSkill);
}
