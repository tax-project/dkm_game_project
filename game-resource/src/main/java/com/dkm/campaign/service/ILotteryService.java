package com.dkm.campaign.service;

import com.dkm.campaign.entity.vo.LotteryBuyResultVo;
import com.dkm.campaign.entity.vo.LotteryInfoVo;

/**
 * @author fkl
 * @date 2020年7月15日
 */
public interface ILotteryService {
    /**
     * 获取全部的神秘商店信息
     *
     * @param userId
     */
    LotteryInfoVo getAllInfo(Long userId);

    /**
     * 刷新
     */
    void refresh();

    /**
     *  购买
     * @return
     */
    LotteryBuyResultVo buy(Long lotteryId, Integer size, Long userId);
}
