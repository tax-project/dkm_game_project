package com.dkm.campaign.service.impl;

import com.dkm.campaign.dao.LotteryItemDao;
import com.dkm.campaign.entity.vo.LotteryInfoVo;
import com.dkm.campaign.service.ILotteryService;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author OpenE
 */
@Service
public class LotteryServiceImpl implements ILotteryService {
    @Resource
    private LotteryItemDao lotteryItemDao;

    @Override
    public LotteryInfoVo getAllInfo() {
        final val lotteryItemEntities = lotteryItemDao.selectAll();
        return null;
    }

    @Override
    public void refresh() {

    }
}
