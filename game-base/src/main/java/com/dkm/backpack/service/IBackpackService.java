package com.dkm.backpack.service;

import com.dkm.backpack.entity.bo.AddBackpackItemBO;

/**
 * @Author: HuangJie
 * @Date: 2020/5/14 15:12
 * @Version: 1.0V
 */
public interface IBackpackService {
    /**
     * 添加背包物品
     * @param backpackItemBO 需要添加的物品的信息
     */
    void addBackpackItem(AddBackpackItemBO backpackItemBO);
}
