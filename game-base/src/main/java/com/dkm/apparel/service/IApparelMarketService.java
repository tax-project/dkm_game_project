package com.dkm.apparel.service;

import com.dkm.apparel.entity.vo.ApparelMarketDetailVo;
import com.dkm.apparel.entity.vo.ApparelOrderVo;
import com.dkm.apparel.entity.vo.ApparelPutVo;

import java.util.List;

/**
 * @description:
 * @author: zhd
 * @create: 2020-06-19 15:28
 **/
public interface IApparelMarketService {

    /**
     * 上架服饰
     * @param apparelPutVo
     * @param userId
     */
    void putOnSell(Long userId, ApparelPutVo apparelPutVo);

    /**
     * 下架商品
     * @param apparelMarketId
     */
    void downApparel(Long apparelMarketId);

    /**
     * 摆摊
     * @param userId
     * @param type
     * @return
     */
    List<ApparelMarketDetailVo> apparelMarket(Long userId, Integer type);

    /**
     * 获取用户正在上架的衣服
     * @param userId
     * @return
     */
    List<ApparelMarketDetailVo> puttingApparel(Long userId);

    /**
     * 服饰交易记录
     * @param userId
     * @return
     */
    List<ApparelOrderVo> getApparelOrders(Long userId);
}
