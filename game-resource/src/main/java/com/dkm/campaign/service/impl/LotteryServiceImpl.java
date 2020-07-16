package com.dkm.campaign.service.impl;

import com.dkm.campaign.dao.LotteryItemDao;
import com.dkm.campaign.dao.OptionsDao;
import com.dkm.campaign.entity.vo.LotteryInfoVo;
import com.dkm.campaign.entity.vo.LotteryItemVo;
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

    @Resource
    private OptionsDao optionsDao;

    @Override
    public LotteryInfoVo getAllInfo(Long userId) {
        final val res = new LotteryInfoVo();
        final val lotteryItemEntities = lotteryItemDao.selectAll(userId);
        res.setRefreshDate(optionsDao.selectNextUpdateDateStr());
        final val items = res.getItems();
        lotteryItemEntities.forEach(it -> {
            final val lotteryItemVo = new LotteryItemVo();
            lotteryItemVo.setId(it.getId());
            lotteryItemVo.setGoodsName(it.getName());
            lotteryItemVo.setGoodsImageUrl(it.getImageUrl());
            lotteryItemVo.setTotal(it.getSize());
            lotteryItemVo.setMarketPrice((int) (it.getMoney() * it.getGoodsSize()));
            lotteryItemVo.setUserParticipation(it.getUserSize());
//            lotteryItemVo.set;
            items.add(lotteryItemVo);
        });
        return res;
    }

    @Override
    public void refresh() {

    }
}
