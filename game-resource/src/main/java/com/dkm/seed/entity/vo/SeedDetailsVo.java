package com.dkm.seed.entity.vo;

import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/1 21:10
 */
@Data
public class SeedDetailsVo {
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
     *种子产出的金币
     */
    private Integer seedProdgold;
    /**
     *首次产出的红包
     */
    private Integer seedProdred;
    /**
     *种子种植金币
     */
    private Integer seedGold;
    /**
     * 种子图片
     */
    private String seedImg;
    /**
     *种子总解锁进度值
     */
    private Integer seedAllUnlock;
    /**
     *当前种子解锁进度
     */
    private Integer seedPresentUnlock;
    /**
     *解锁一次加的声望
     */
    private Integer prestige;
    /**
     * 解锁碎片金币
     */
   private Integer UnlockFragmentedGoldCoins;
    /**
     * 种子状态
     */
    private Integer seedStatus;
}
