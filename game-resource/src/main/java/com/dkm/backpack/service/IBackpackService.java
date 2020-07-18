package com.dkm.backpack.service;

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
}
