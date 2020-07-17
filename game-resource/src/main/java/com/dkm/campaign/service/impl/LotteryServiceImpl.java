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
            //設置代號
            lotteryItemVo.setId(it.getId());
            // 设置商品名称
            lotteryItemVo.setGoodsName(it.getName());
            // 商品数目
            lotteryItemVo.setGoodsSize(it.getGoodsSize());
            // 设置商品图片URL
            lotteryItemVo.setGoodsImageUrl(it.getImageUrl());
            // 奖品的总价值
            lotteryItemVo.setMarketPrice((int) (it.getMoney() * it.getGoodsSize()));
            // 设置奖品的总数目
            lotteryItemVo.setPrizeSize(it.getSize());
            // 用户已经参与的数目
            lotteryItemVo.setPrizeAlreadyUsedSize(it.getUsedSize());
            // 自己参与的次数
            lotteryItemVo.setUserAlreadyUsedSize(it.getUserSize());
            items.add(lotteryItemVo);
        });
        return res;
    }

    @Override
    public void refresh() {

    }
}
