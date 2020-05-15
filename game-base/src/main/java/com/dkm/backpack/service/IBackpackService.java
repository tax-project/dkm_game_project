package com.dkm.backpack.service;

/**
 * @Author: HuangJie
 * @Date: 2020/5/14 15:12
 * @Version: 1.0V
 */
public interface IBackpackService {
    /**
     * 添加背包物品
     * @param itemId 物品的ID
     * @param itemTableName 物品所在表
     */
    void addBackpackItem(Long itemId, String itemTableName);
}
