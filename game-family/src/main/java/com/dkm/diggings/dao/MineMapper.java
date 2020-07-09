package com.dkm.diggings.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.diggings.bean.entity.MineEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dragon
 */
@Component
public interface MineMapper extends BaseMapper<MineEntity> {

    /**
     * 一次性添加所有表
     */
    int insertAll(@Param("list") List<MineEntity> list);
}
