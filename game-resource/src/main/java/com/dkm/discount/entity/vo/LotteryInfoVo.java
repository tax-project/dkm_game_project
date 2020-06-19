package com.dkm.discount.entity.vo;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * 神秘商店的信息
 */
@Data
public class LotteryInfoVo {
    /**
     * 下次刷新的时间，格式化为 [yyyy-MM-dd HH:mm:ss]
     */
    private String nextRefreshDate;

    /**
     * 抽奖的信息
     */
    final private List<LotteryItemVo> lotteryList = Collections.emptyList();

    /**
     * 上次抽奖中中奖的用户
     */
    final private List<LastLotteryUserVo> lastLotteryUserList  = Collections.emptyList();
}
