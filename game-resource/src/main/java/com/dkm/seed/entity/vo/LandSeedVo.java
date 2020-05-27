package com.dkm.seed.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: dkm_game
 * @DESCRIPTION:
 * @USER: 刘梦祺
 * @DATE: 2020/5/12 16:47
 */
@Data
public class LandSeedVo {
    /**
     * 种子id
     */
    private Integer seedId;
    /**
     * 种子名称
     */
    private String seedName;
    /**
     * 种子等级
     */
    private Integer seedGrade;
    /**
     * 种子成熟时间
     */
    private Long seedGrowuptime;
    /**
     *种子产出的金币
     */
    private Integer seedProdgold;
    /**
     *首次产出的红包
     */
    private Integer seedProdred;
    /**
     *种子产出的经验
     */
    private Integer seedExperience;
    /**
     *种子总解锁进度值
     */
    private Integer seedSumUnlock;
    /**
     *当前种子解锁进度
     */
    private Integer seedPresentUnlock;
    /**
     *种子种植金币
     */
    private Integer seedGold;
    /**
     *种子成熟时间
     */
    private LocalDateTime plantTime;
}
