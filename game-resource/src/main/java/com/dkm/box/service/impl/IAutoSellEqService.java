package com.dkm.box.service.impl;

import java.util.List;

/**
 * @description:
 * @author: zhd
 * @create: 2020-08-03 09:39
 **/
public interface IAutoSellEqService {

    /**
     * 设置自动出售信息
     * @param sellInfo
     * @param userId
     */
    void setAutoSell(Long userId,String sellInfo);

    /**
     * 获取用户自动出售信息
     * @param userId
     * @return
     */
    List<Integer> getAutoSellInfo(Long userId);
}
