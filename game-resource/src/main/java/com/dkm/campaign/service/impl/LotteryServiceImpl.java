package com.dkm.campaign.service.impl;

import com.dkm.campaign.entity.vo.LotteryInfoVo;
import com.dkm.campaign.service.ILotteryService;
import com.dkm.campaign.service.ILotterySystemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author OpenE
 */
@Service
public class LotteryServiceImpl implements ILotteryService {
    @Resource
    private ILotterySystemService lotterySystemService;

    @Override
    public LotteryInfoVo getAllInfo() {

        return null;
    }
}
