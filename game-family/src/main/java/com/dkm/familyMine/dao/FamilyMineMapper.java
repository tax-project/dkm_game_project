package com.dkm.familyMine.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.familyMine.entity.MineEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyMineMapper extends BaseMapper<MineEntity>  {

    @Select("SELECT  * FROM  `tb_family_mine` where familyId = ${id}")
    List<MineEntity> getFamilyMineById(Long id);
}
