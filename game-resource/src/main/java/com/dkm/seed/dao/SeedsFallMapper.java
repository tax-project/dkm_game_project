package com.dkm.seed.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.seed.entity.Seed;
import com.dkm.seed.entity.SeedsFall;
import com.dkm.seed.entity.vo.GoldRedVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/8 11:41
 */
@Component
public interface SeedsFallMapper extends BaseMapper<SeedsFall> {
    /**
     * 批量增加掉落数据
     * @param list
     */
    void insertSeedDropGoldOrRedEnvelopes(List<SeedsFall> list);

    /**
     * 统计掉落的金币和红包
     * @param userId
     * @return
     */
    GoldRedVo queryGoldAndRed(Long userId);
}
