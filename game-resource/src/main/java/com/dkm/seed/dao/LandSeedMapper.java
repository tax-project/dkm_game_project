package com.dkm.seed.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.seed.entity.LandSeed;
import com.dkm.seed.entity.Seed;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/6 16:16
 */
@Component
public interface LandSeedMapper extends BaseMapper<LandSeed> {

    /**
     * 批量修改
     * @param list
     * @return
     */
    int updateSeedStatus(List<Long> list);

    /**
     *  把种子状态修改为3
     * @param userId
     * @return
     */
    int updateStatus (Long userId);
}
