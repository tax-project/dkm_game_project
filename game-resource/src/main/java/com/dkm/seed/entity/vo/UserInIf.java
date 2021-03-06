package com.dkm.seed.entity.vo;

import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/16 16:40
 */
@Data
public class UserInIf {
    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 用户金币
     */
    private Integer userGold;
    /**
     * 用户红包可用余额
     */
    private Double userInfoPacketBalance;
    /**
     * 用户当前经验
     */
    private Integer userInfoNowExperience;
    /**
     * 用户下一等级所需经验值
     */
    private Integer userInfoNextExperience;

    /**
     * 用户等级
     */
    private Integer userInfoGrade;

}
