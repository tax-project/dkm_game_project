package com.dkm.seed.service;

import com.dkm.seed.entity.SeedsFall;
import com.dkm.seed.entity.bo.SeedDropBO;
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
     *   前端调掉落接口
     *
     * @return 掉落的东西
     */
    SeedDropBO seedDrop(Integer userInfoGrade);

    /**
     *  上线就调用的接口
     * @param seedId 种子id
     * @param userInfoGrade 等级
     * @return 返回所有掉落的信息
     */
    List<SeedDropBO> redBagDroppedSeparately(Long seedId, Integer userInfoGrade);

}
