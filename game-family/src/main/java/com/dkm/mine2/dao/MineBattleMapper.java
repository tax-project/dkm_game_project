package com.dkm.mine2.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.mine2.bean.entity.MineBattleEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author OpenE
 */
@Component
public interface MineBattleMapper extends BaseMapper<MineBattleEntity> {

    MineBattleEntity selectByFamilyId(@Param("familyId") Long familyId);

    MineBattleEntity selectByEmpty();
}
