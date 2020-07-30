package com.dkm.mallEvents.service;

import com.dkm.mallEvents.entities.data.CancerGiftBoxInfoVo;
import com.dkm.mallEvents.entities.data.LuckyGiftVo;
import com.dkm.mallEvents.entities.data.RechargeVo;


public interface ICancerService {
    RechargeVo getSingleTopUp(Long id);

    Boolean getSingleTopUpInfoCheck(Long userId, Integer id);

    RechargeVo getCumulativeRecharge(Long id);

    Boolean receiveTopUpReward(Long userId, Integer itemId);

    RechargeVo getConsumerRecharge(Long id);

    Boolean receiveConsumerReward(Long id, Integer itemId);

    Boolean consumptionKingReward(Long id, Integer id1);

    RechargeVo consumptionKing(Long id);

    RechargeVo consumptionPurple(Long id);

    Boolean consumptionKingPurple(Long id, Integer id1);

    RechargeVo consumptionBlue(Long id);

    Boolean consumptionKingBlue(Long id, Integer id1);

    RechargeVo consumptionPurpleOffer(Long id);

    Boolean consumptionKingPurpleOffer(Long id, Integer id1);

    Boolean consumptionKingOrange(Long id, Integer id1);

    RechargeVo consumptionOrange(Long id);

    LuckyGiftVo getLuckyGiftInfo();

    CancerGiftBoxInfoVo getCancerGiftBoxInfo();

}
