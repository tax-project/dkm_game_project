package com.dkm.turntable.service;

import com.dkm.turntable.entity.vo.AddGoodsInfoVo;
import com.dkm.turntable.entity.vo.TurntableInfoVo;

import java.util.List;

/**
 * @description: zhuanpan
 * @author: zhd
 * @create: 2020-06-11 09:57
 **/
public interface ITurntableService {

    /**
     * 获取转盘数据
     * @param userId
     * @param type
     * @return
     */
    List<TurntableInfoVo> getTurntable(Long userId, Integer type);


    /**
     * 增加背包物品
     * @param userId
     * @param addGoodsInfoVo
     */
    void addGoods(Long userId, AddGoodsInfoVo addGoodsInfoVo);
}
