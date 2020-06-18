package com.dkm.goldMine.bean.vo;

import lombok.Data;

/**
 *
 */
@Data
public class MineItemFightVo {
    /**
     * 是否为npc
     */
    boolean npc;

    boolean occupied;
    /**
     * 用户ID
     */
    long userId = 0;
    /**
     * 家族id
     */
    long userFamilyId = 0;
    /**
     * 对手名称
     */
    String fightName;
    /**
     * 家族等级
     */
    int ourLevel;
    /**
     * 对手等级
     */
    int npcLevel;
    /**
     * 成功率
     */
    String successRate;
    /**
     * 占领时间 ，如果为npc则为空
     */
    String occupiedStartDate = "";
    /**
     * 结束时间 ，如果为npc则为空
     */
    String occupiedEndDate = "";
    /**
     * 小时产量
     */

    final ProfitPerHourVo profitPerHour = new ProfitPerHourVo();

}
