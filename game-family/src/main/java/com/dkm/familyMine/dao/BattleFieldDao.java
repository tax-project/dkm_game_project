package com.dkm.familyMine.dao;

import com.dkm.familyMine.entity.MineBattleFieldEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface BattleFieldDao {
    @Insert("INSERT INTO tb_family_mine_battle_field(battle_field_id) VALUES (#{id})")
    void createBattleFieldById(Long id);

    @Select("SELECT * FROM tb_family_mine_battle_field WHERE familyIdByFirst = #{familyId} or  familyIdBySecond = #{familyId} or  familyIdByThird  = #{familyId} or familyIdByForth = #{familyId}")
    MineBattleFieldEntity getBattleFieldByFamilyId(long familyId);

}
