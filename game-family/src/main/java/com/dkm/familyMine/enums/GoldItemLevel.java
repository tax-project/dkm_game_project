package com.dkm.familyMine.enums;

/**
 * 矿区等级与产量
 */
enum GoldItemLevel {

    LEVEL_1(  1, 8280 , 300),
    LEVEL_2(  2, 12420, 300),
    LEVEL_3(  3, 16560, 360),
    LEVEL_4(  4, 20700, 420),
    LEVEL_5(  5, 62100,1440),
    LEVEL_6(  6, 66300,1560),
    LEVEL_7(  7, 70300,1680),
    LEVEL_8(  8, 74500,1800),
    LEVEL_9(  9, 91080,1920),
    LEVEL_10(10,107700,2040),
    LEVEL_11(11,124200,2160),
    LEVEL_12(12,140760,2280),
    LEVEL_13(13,165600,2400),
    LEVEL_14(14,190300,2520);

    private final int level;
    private final int goldYield;
    private final int integralYield;

    GoldItemLevel(int level, int goldYield, int integralYield){
            this.level = level;
            this.goldYield = goldYield;
            this.integralYield = integralYield;
     }

    public int getLevel() {
        return level;
    }

    public int getGoldYield() {
        return goldYield;
    }

    public int getIntegralYield() {
        return integralYield;
    }
}
