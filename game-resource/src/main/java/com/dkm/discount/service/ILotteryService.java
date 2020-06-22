package com.dkm.discount.service;

import com.dkm.discount.bean.vo.LotteryInfoVo;

public interface ILotteryService {

    LotteryInfoVo getAllLotteries(String userId);
}
