package com.dkm.goldMine.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.goldMine.bean.entity.MineEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MineMapper extends BaseMapper<MineEntity> {
    MineEntity selectMineByFamilyId(@Param("familyId")Long id);
}
