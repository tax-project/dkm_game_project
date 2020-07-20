package com.dkm.backpack.service;

import com.dkm.backpack.entity.bo.AddGoodsInfo;
import com.dkm.backpack.entity.bo.SellGoodsInfo;
import com.dkm.backpack.entity.vo.UserBackpackGoodsVo;

import java.util.List;

/**
 * @description: 背包
 * @author: zhd
 * @create: 2020-07-17 21:51
 **/
public interface IBackpackService {
    /**
     * 获取用户背包数据
     * @param userId
     * @return
     */
    List<UserBackpackGoodsVo> getUserBackpackGoods(Long userId);

    /**
     * 背包添加减少数据
     * @param addGoodsInfo
     */
    void  addBackpackGoods(AddGoodsInfo addGoodsInfo);
    /**
     * 背包出售物品
     * @param sellGoodsInfo
     */
    void sellBackpackGoods(SellGoodsInfo sellGoodsInfo);
}
