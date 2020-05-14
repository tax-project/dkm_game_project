package com.dkm.seed.service;

import com.dkm.land.entity.vo.Message;
import com.dkm.seed.entity.LandSeed;
import com.dkm.seed.entity.Seed;
import com.dkm.seed.entity.vo.LandSeedVo;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: dkm_game
 * @DESCRIPTION:
 * @USER: 刘梦祺
 * @DATE: 2020/5/11 16:17
 */
public interface ISeedService {
    /**
     * 根据用户id得到种子（是否解锁）
     */
    List<Seed> queryUserIdSeed();

    /**
     * 解锁植物
     * @return
     */
    Message unlockplant(Integer unlockmoeny,Integer grade,Integer seedid,Integer seedPresentUnlock);
    /**
     * 查询已种植的种植
     *
     */
    List<LandSeedVo> queryAlreadyPlantSeed(LandSeed landSeed);

    /**
     * 根据种子查询种子
     *
     */
    Seed querySeedById(Integer seeId);
}
