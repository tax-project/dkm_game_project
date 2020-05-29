package com.dkm.gift.service;

import com.dkm.gift.entity.GiftEntity;

import java.util.List;

/**
 * @description: 礼物
 * @author: zhd
 * @create: 2020-05-27 09:26
 **/
public interface GiftService {

    /**
     * 获取礼品列表
     * @param type
     * @return
     */
    List<GiftEntity> getAllGift(Integer type);

}
