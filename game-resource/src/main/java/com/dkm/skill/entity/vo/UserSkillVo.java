package com.dkm.skill.entity.vo;

import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/23 20:37
 */
@Data
public class UserSkillVo {
    /**
     *技能主键
     */
    private long skId;
    /**
     *技能名称
     */
    private String skName;
    /**
     *技能图片
     */
    private String skImg;
    /**
     * 主键
     */
    private long usId;
    /**
     * 用户id
     */
    private long userId;
    /**
     * 技能等级
     */
    private Integer skGrad;
    /**
     * 数量的
     */
    private String skDegreeProficiency;
    /**
     * 当前成功率
     */
    private Integer skCurrentSuccessRate;
}
