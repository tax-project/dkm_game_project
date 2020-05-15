package com.dkm.pets.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.pets.entity.FoodEntity;
import com.dkm.pets.entity.dto.BackPackFood;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author  zhd
 */
@Repository
public interface FoodMapper extends BaseMapper<FoodEntity> {

    /**
     * 查询背包食物信息
     * @param userId
     * @return
     */
    @Select("SELECT fu.id,fu.food_number,fd.food_name,fd.food_url,fd.food_gold FROM (SELECT * FROM tb_food   WHERE user_id  = #{userId}) fu LEFT JOIN tb_food_detail fd on fd.food_id = fu.food_id")
    List<BackPackFood> getPackFood(@Param("userId") Long userId);

    /**
     * 出售修改食物数量
     * @param sellNumber
     * @param id
     * @return
     */
    @Update("update tb_food set food_number = food_number - #{sellNumber} where id = #{id}")
    Integer sellFood(@Param("sellNumber") Integer sellNumber,@Param("id") Long id);
}
