package com.dkm.diggings.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.diggings.bean.entity.MineBattleEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author OpenE
 */
@Component
public interface MineBattleMapper extends BaseMapper<MineBattleEntity> {

    MineBattleEntity selectByFamilyId(@Param("familyId") Long familyId);

    MineBattleEntity selectByEmpty();
}
