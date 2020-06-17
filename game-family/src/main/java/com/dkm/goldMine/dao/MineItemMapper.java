package com.dkm.goldMine.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.goldMine.bean.entity.MineEntity;
import com.dkm.goldMine.bean.entity.MineItemEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MineItemMapper extends BaseMapper<MineItemEntity> {

    List<MineItemEntity> selectByBattleId(@Param("battle_id") Long battleId);

    MineItemEntity selectByBattleIdAndGoldItemId(@Param("family_id")  Long familyId, @Param("gold_item_id") long goldItemId);
}
