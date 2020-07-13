package com.dkm.diggings.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.diggings.bean.entity.DiggingsHistoryEntity;
import com.dkm.diggings.bean.other.Pair;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author OpenE
 */
public interface DiggingsHistoryMapper extends BaseMapper<DiggingsHistoryEntity> {


    /**
     * 查询最后一条数据
     */
    DiggingsHistoryEntity selectLastOneByUserIdAndFamilyId(@Param("userId") Long userId, @Param("familyId") Long familyId);

    List<DiggingsHistoryEntity> selectAccept(@Param("list") List<Pair<Long, Long>> list, @Param("familyId") Long familyId, @Param("date") LocalDateTime date);
}
