package com.dkm.diggings.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.diggings.bean.entity.DiggingsEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author OpenE
 */
@Component
public interface DiggingsMapper extends BaseMapper<DiggingsEntity> {

    DiggingsEntity selectByFamilyId(@Param("familyId") Long familyId);

    DiggingsEntity selectByEmpty();
}
