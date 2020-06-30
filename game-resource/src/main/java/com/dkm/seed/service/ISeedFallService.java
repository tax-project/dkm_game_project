package com.dkm.seed.service;

import com.dkm.seed.entity.vo.GoldOrMoneyVo;
import com.dkm.seed.entity.vo.SeedsFallVo;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/8 15:18
 */
public interface ISeedFallService {
    /**
     * 种子掉落(金币  红包)
     */
    List<GoldOrMoneyVo> seedDrop();

    /**
     * 单独掉落红包
     */
    List<Double> redBagDroppedSeparately(Double money);


    /**
     * 查询已经掉落的金币红包和花
     */
    List<SeedsFallVo> queryDroppedItems();

    /**
     * 批量修改种子状态为2 待收取状态
     * @param list
     * @return
     */
    int updateLeStatus(List<Long> list);


}
