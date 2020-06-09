package com.dkm.feign.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/28 19:55
 */
@Data
public class SeedPlantUnlock {
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
    private LocalDateTime seedGrowuptime;
    /**
     *种子产出的金币
     */
    private Integer seedProdgold;
    /**
     *
     */
    private Integer seedProdred;
    /**
     *种子产出的经验
     */
    private Integer seedExperience;
    /**
     *种子种植金币
     */
    private Integer seedGold;
    /**
     *
     */
    private String exp1;
    /**
     *
     */
    private String exp2;
    /**
     *
     */
    private String exp3;
    /**
     *种子图片
     */
    private String seedImg;
    /**
     * 当前解锁进度
     */
    private Integer seedPresentUnlock=0;
    /**
     *总解锁进度
     */
    private Integer seedAllUnlock;
    /**
     *状态 0未解锁 1解锁
     */
    private Integer seedStatus=0;
}
