package com.dkm.seed.entity.vo;

import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/29 13:43
 */
@Data
public class SeedPlantVo {
    /**
     * 种子种植id
     */
     private Long id;
    /**
     * 种子id
     */
    private Integer seedId;
    /**
     * 等级
     */
    private Integer seedGrade;
    /**
     * 种子种植金币
     */
    private Integer seedGold;
    /**
     * 用来做判断
     */
    private Integer status;
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
    /**
     * 掉落金币
     */
    private Integer dropGoldCoin;
    /**
     * 掉落的红包
     */
    private Double redPacketDropped;
}
