package com.dkm.goldMine.bean.vo;

import lombok.Data;

@Data
public class FightVo {
    /**
     * 攻击结果
     */
    boolean fightStatus ;
    /**
     * 每秒增加的金币
     */
    int addMoneyOnSecond;
    /**
     * 每秒增加的积分
     */
    int addIntegralOnSecond;


}
