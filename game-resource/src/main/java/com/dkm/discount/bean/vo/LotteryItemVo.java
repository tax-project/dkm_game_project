package com.dkm.discount.bean.vo;


import lombok.Data;

/**
 * 神秘商店下夺宝物品的信息
 * @author OpenE
 */
@Data
public class LotteryItemVo {

    /**
     * 宝物 ID
     */
    private long commodityId;
    /**
     * 宝物名称
     */
    private String commodityName;
    /**
     * 宝物个数
     */
    private long commoditySize;
    /**
     * 奖劵数目
     */
    private long prizeSize;
    /**
     * 奖劵已购买数目
     */
    private long prizeBought;
    /**
     * 用户已经购买的次数
     */
    private long userBoughtSize;

}
