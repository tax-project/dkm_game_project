package com.dkm.family.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.family.entity.FamilyEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 家族
 * @author: zhd
 * @create: 2020-05-20 11:29
 **/
@Repository
public interface FamilyDao extends BaseMapper<FamilyEntity> {

    @Select("SELECT*FROM tb_family WHERE family_id>=((" +
            "SELECT max(family_id) FROM tb_family)-(" +
            "SELECT min(family_id) FROM tb_family))*RAND()+(" +
            "SELECT MIN(family_id) FROM tb_family) LIMIT 10")
    List<FamilyEntity> getHotFamily();
}
