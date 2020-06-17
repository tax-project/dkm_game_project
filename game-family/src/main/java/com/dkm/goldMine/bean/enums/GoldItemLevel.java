package com.dkm.goldMine.bean.enums;

/**
 * 矿区等级与产量
 */
public enum GoldItemLevel {

    LEVEL_1(  1, 8280 , 300, "初级守护者",1),
    LEVEL_2(  2, 12420, 300, "初级守护者",2),
    LEVEL_3(  3, 16560, 360, "初级守护者",3),
    LEVEL_4(  4, 20700, 420, "中级守护者",4),
    LEVEL_5(  5, 62100,1440, "中级守护者",5),
    LEVEL_6(  6, 66300,1560, "中级守护者",6),
    LEVEL_7(  7, 70300,1680, "中级守护者",7),
    LEVEL_8(  8, 74500,1800, "中级守护者",8),
    LEVEL_9(  9, 91080,1920, "中级守护者",9),
    LEVEL_10(10,107700,2040, "中级守护者",10),
    LEVEL_11(11,124200,2160, "高级守护者",11),
    LEVEL_12(12,140760,2280, "高级守护者",12),
    LEVEL_13(13,165600,2400, "高级守护者",13),
    LEVEL_14(14,190300,2520, "高级守护者",14);

    private final int level;
    private final long goldYield;
    private final long integralYield;
    private final String npcName;
    private final int npcLevel;

    GoldItemLevel(int level, int goldYield, int integralYield,String npcName,int npcLevel){
            this.level = level;
            this.goldYield = goldYield;
            this.integralYield = integralYield;
            this.npcName = npcName;
            this.npcLevel = npcLevel;
     }

    public int getLevel() {
        return level;
    }

    public Long getGoldYield() {
        return goldYield;
    }

    public Long getIntegralYield() {
        return integralYield;
    }

    public String getNpcName() {
        return npcName;
    }

    public int getNpcLevel() {
        return npcLevel;
    }
}
