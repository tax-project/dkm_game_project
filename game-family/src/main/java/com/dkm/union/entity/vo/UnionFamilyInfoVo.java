package com.dkm.union.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-06-18 11:35
 **/
@Data
public class UnionFamilyInfoVo {

    /**
     * 家族id
     */
    private Long familyId;
    /**
     * 家族名字
     */
    private String familyName;
    /**
     * 家族介绍
     */
    private String familyIntroduce;
    /**
     * 家族欢迎词
     */
    private String familyWelcomeWords;

    /**
     * 成员头像
     */
    private List<String> imgs;
    /**
     * 成员头像
     */
    private Integer familyUserNumber;
}
