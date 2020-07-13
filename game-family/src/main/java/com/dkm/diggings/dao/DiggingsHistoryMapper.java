package com.dkm.diggings.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.diggings.bean.entity.DiggingsHistoryEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @author OpenE
 */
public interface DiggingsHistoryMapper extends BaseMapper<DiggingsHistoryEntity> {


    /**
     * 查询最后一条数据
     */
    DiggingsHistoryEntity selectLastOneByUserIdAndFamilyId(@Param("userId") Long userId, @Param("familyId") Long familyId);

}
