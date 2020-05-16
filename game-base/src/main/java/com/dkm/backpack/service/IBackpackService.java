package com.dkm.backpack.service;

import com.dkm.backpack.entity.vo.BackpackItemVO;

import java.util.List;

/**
 * @Author: HuangJie
 * @Date: 2020/5/14 15:12
 * @Version: 1.0V
 */
public interface IBackpackService {
    /**
     * 获取背包所有物品
     */
    List<BackpackItemVO> allBackpackItem();
}
