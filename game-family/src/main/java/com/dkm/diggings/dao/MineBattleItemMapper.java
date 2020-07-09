package com.dkm.diggings.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.diggings.bean.entity.MineBattleItemEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dragon
 */
@Component
public interface MineBattleItemMapper extends BaseMapper<MineBattleItemEntity> {

    /**
     * 一次性添加所有表
     *
     * @param list
     * @return
     */
    int insertAll(@Param("list") List<MineBattleItemEntity> list);
}
