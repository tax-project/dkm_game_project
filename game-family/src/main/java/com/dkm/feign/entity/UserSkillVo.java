package com.dkm.feign.entity;

import lombok.Data;

/**
 * @Author Administrator
 * @Date 2020/7/7 10:48
 * @Version 1.0
 */
@Data
public class UserSkillVo {

    /**
     * 等级
     */
    private Integer skGrade;

    /**
     * 技能名字
     */
    private String skName;
}
