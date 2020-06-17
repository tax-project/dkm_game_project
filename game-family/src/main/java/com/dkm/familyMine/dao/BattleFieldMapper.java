package com.dkm.familyMine.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.familyMine.entity.MineBattleFieldEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


@Repository
public interface BattleFieldMapper extends BaseMapper<MineBattleFieldEntity> {
    @Insert("INSERT INTO tb_family_mine_battle_field(battle_field_id) VALUES (${id})")
    MineBattleFieldEntity createBattleFieldById(Long id);

    @Select("SELECT * FROM tb_family_mine_battle_field WHERE familyIdByFirst = ${familyId} or  familyIdBySecond = ${familyId} or  familyIdByThird  = ${familyId} or familyIdByForth = ${familyId}")
    MineBattleFieldEntity getBattleFieldByFamilyId(long familyId);

    @Select("SELECT * FROM tb_family_mine_battle_field WHERE familyIdByFirst = 0 or  familyIdBySecond = 0 or  familyIdByThird  = 0 or familyIdByForth = 0")
    MineBattleFieldEntity selectEmptyMineBattle();



//    void save(MineBattleFieldEntity entity);
    @Update("UPDATE `tb_family_mine_battle_field` SET familyIdByFirst = ${entity.familyIdByFirst} , familyIdBySecond = ${entity.familyIdBySecond}," +
            "familyIdByThird = ${entity.familyIdByThird} ,familyIdByForth = ${entity.familyIdByForth} WHERE battle_field_id = ${entity.battleFieldId}")
    void update(MineBattleFieldEntity entity);
}
