package com.dkm.seed.service;

import com.dkm.seed.entity.vo.GoldOrMoneyVo;

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




}
