package com.dkm.apparel.service;

/**
 * @description:
 * @author: zhd
 * @create: 2020-06-19 15:28
 **/
public interface IApparelMarketService {

    /**
     * 上架服饰
     * @param apparelId
     * @param userId
     */
    void putOnSell(Long userId,Long apparelId);

}
