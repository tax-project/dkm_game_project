package com.dkm.discount.service;

import com.dkm.discount.entity.vo.LotteryInfoVo;

public interface ILotteryService {

    LotteryInfoVo getAllLotteries(String userId);
}
