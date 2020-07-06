package com.dkm.skill.service;

import com.dkm.skill.entity.vo.SkillUserSkillVo;

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

}
