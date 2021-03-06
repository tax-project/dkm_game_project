package com.dkm.skill.service;

import com.dkm.skill.entity.Stars;
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
public interface IStarsService {

    /**
     * 根据用户id查询自己当前的金星星
     * @param userId
     * @return
     */
    Stars queryCurrentConsumeByUserId(Long userId);

    /**
     * 初始化用户拥有金星星的数量
     * @return
     */
    int addUserVenusNum(Long userId);

    /**
     *  减去用户金星星数量
     * @return
     */
    int updateUserVenusNum(Stars stars);
}
