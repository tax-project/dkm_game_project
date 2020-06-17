package com.dkm.goldMine.bean.vo;

import lombok.Data;

@Data
public class MineItemNpcVo {
    Boolean npc;
    long userId = 0;
    long userFamilyId  = 0;
    String npcName ;
    int npcLevel ;
    String occupiedStartDate = "";
    String occupiedEndDate = "";
    final ProfitPerHourVo profitPerHour = new ProfitPerHourVo();

}
