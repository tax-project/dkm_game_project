package com.dkm.skill.entity.vo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @author MQ
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/7/4 14:44
 */
@Data
public class SkillUserSkillVo extends Model<SkillUserSkillVo> {

    /**
     * 技能主键id
     */
    private Long id;

    /**
     * 技能名字
     */
    private String skName;

    /**
     * 等级
     */
    private Integer skGrade;


    /**
     * 当前技能升级成功率
     */
    private Integer skCurrentSuccessRate;

    /**
     * 熟练度
     */
    private Integer skDegreeProficiency;

    /**
     * 升级一级增加的声望
     */
    private Integer skAddPrestige;

    /**
     * 消耗的总个数
     */
    private Integer skAllConsume;

}
