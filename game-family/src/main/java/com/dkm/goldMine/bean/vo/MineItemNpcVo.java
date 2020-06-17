package com.dkm.goldMine.bean.vo;

import lombok.Data;

@Data
public class MineItemNpcVo {
    long userId;
    long userFamilyId;
    String npcName;
    int level;
    String occupiedStartDate = "";
    String occupiedEndDate = "";
    final ProfitPerHourVo profitPerHour = new ProfitPerHourVo();
}
