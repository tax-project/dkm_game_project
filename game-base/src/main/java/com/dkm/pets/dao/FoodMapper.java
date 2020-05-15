package com.dkm.pets.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.pets.entity.FoodEntity;
import com.dkm.pets.entity.dto.BackPackFood;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author  zhd
 */
@Repository
public interface FoodMapper extends BaseMapper<FoodEntity> {

    @Select("SELECT fu.food_number,fd.food_name,fd.food_url,fd.food_gold FROM (SELECT * FROM tb_food WHERE user_id  = #{userId}) fu LEFT JOIN tb_food_detail fd on fd.food_id = fu.food_id")
    List<BackPackFood> getPackFood(@Param("userId") Long userId);
}
