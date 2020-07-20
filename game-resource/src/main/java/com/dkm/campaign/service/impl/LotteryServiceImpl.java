package com.dkm.campaign.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.campaign.dao.LotteryItemDao;
import com.dkm.campaign.dao.LotteryUserDao;
import com.dkm.campaign.dao.LotteryHistoryDao;
import com.dkm.campaign.dao.OptionsDao;
import com.dkm.campaign.entity.LotteryHistoryEntity;
import com.dkm.campaign.entity.LotteryUserEntity;
import com.dkm.campaign.entity.vo.LotteryBuyResultVo;
import com.dkm.campaign.entity.vo.LotteryInfoVo;
import com.dkm.campaign.entity.vo.LotteryItemVo;
import com.dkm.campaign.entity.vo.LotteryLastVo;
import com.dkm.campaign.service.ILotteryService;
import com.dkm.config.RedisConfig;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.UserFeignClient;
import com.dkm.feign.entity.UserNameVo;
import com.dkm.knapsack.domain.bo.IncreaseUserInfoBO;
import com.dkm.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author OpenE
 */
@Slf4j
@Service
public class LotteryServiceImpl implements ILotteryService {
    @Resource
    private LotteryItemDao lotteryItemDao;

    @Resource
    private OptionsDao optionsDao;

    @SuppressWarnings("unused")
    @Autowired
    private UserFeignClient userFeignClient;

    @Resource
    private LotteryUserDao lotteryUserDao;

    @Resource
    private RedisConfig redisConfig;

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
        log.info(optionsDao.selectRefreshDateStr());
        val localDateTime = DateUtils.parseDateTime(optionsDao.selectNextUpdateDateStr());
        if (localDateTime.isBefore(LocalDateTime.now())) {
            log.info("需要刷新了");
            optionsDao.updateNextUpdateDate(DateUtils.formatDateTime(localDateTime.plusSeconds(Integer.parseInt(optionsDao.selectRefreshDateStr()))));
        }
    }

    @Override
    public LotteryBuyResultVo buy(Long lotteryId, Integer size, Long userId) {
        // 确定此用户钻石余额是否足够
        IncreaseUserInfoBO increaseUserInfoBO = new IncreaseUserInfoBO();
        increaseUserInfoBO.setUserId(userId);
        increaseUserInfoBO.setUserInfoDiamonds(size);
        val data = userFeignClient.cutUserInfo(increaseUserInfoBO);
        if (data.getCode() == 1006) {
            return new LotteryBuyResultVo(false, "钻石余额不足！");
        }
        int remainingSize = lotteryUserDao.selectRemainingSize(lotteryId);
        if (remainingSize < size) {
            return new LotteryBuyResultVo(false, "奖池已满！");
        }
        LotteryUserEntity lotteryUserEntity = new LotteryUserEntity();
        lotteryUserEntity.setTbLotteryId(lotteryId);
        lotteryUserEntity.setUserId(userId);
        LotteryUserEntity[] array = new LotteryUserEntity[size];
        Arrays.fill(array, lotteryUserEntity);
        val integer = lotteryUserDao.insertAll(array);
        log.debug("用户 {} 购买了 {} 个礼包 .", userId, integer);
        return new LotteryBuyResultVo(true, "购买成功");
    }

    @Resource
    private LotteryHistoryDao lotteryHistoryDao;

    @Override
    public List<LotteryLastVo> getLotteryLastVo() {
        List<LotteryHistoryEntity> list = lotteryHistoryDao.selectTenList();
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<LotteryLastVo> result = new ArrayList<>();
        val listResult = userFeignClient.queryUserName(list.stream()
                .map(LotteryHistoryEntity::getUserId).collect(Collectors.toList()));
        if (listResult.getCode() != CodeType.SUCCESS.getCode()) {
            throw new ApplicationException(CodeType.DATABASE_ERROR, "无法得到用户名！");
        }
        if (list.size() != listResult.getData().size()) {
            throw new ApplicationException(CodeType.DATABASE_ERROR, "查询到的用户名不匹配！");
        }
        for (int i = 0; i < list.size(); i++) {
            val lotteryLastVo = new LotteryLastVo();
            UserNameVo userNameVo = listResult.getData().get(i);
            LotteryHistoryEntity lotteryHistoryEntity = list.get(i);
            lotteryLastVo.setUserId(userNameVo.getUserId());
            lotteryLastVo.setMessage(lotteryHistoryEntity.getPrizeText());
            lotteryLastVo.setDiamonds(lotteryHistoryEntity.getDiamonds());
            lotteryLastVo.setUserName(userNameVo.getWeChatNickName());
            result.add(lotteryLastVo);
        }
        return result;
    }
}
