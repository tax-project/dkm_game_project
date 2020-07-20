package com.dkm.seed.service;

import com.dkm.seed.entity.SeedsFall;
import com.dkm.seed.entity.vo.GoldOrMoneyVo;
import com.dkm.seed.entity.vo.SeedsFallVo;

import java.util.List;

/**
 * @author MQ
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/8 15:18
 */
public interface ISeedFallService {
    /**
     * 种子掉落(金币  红包)
     */
    void seedDrop();

    /**
     * 单独掉落红包
     */
    void redBagDroppedSeparately();


    /**
     * 查询已经掉落的金币红包和花
     */
    List<SeedsFallVo> queryDroppedItems();





}
