package com.dkm.goldMine.bean.vo;

import lombok.Data;

@Data
public class FightKillVo {
    boolean kill = true;
    String killMessage  = "已停止挖矿";
    /**
     * 增加的金币
     */
    int addMoney;
    /**
     * 增加的积分
     */
    int addIntegral;


}
