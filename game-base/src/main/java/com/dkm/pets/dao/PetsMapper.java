package com.dkm.pets.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.pets.entity.PetUserEntity;
import com.dkm.pets.entity.dto.EatFoodDto;
import com.dkm.pets.entity.dto.FoodInfo;
import com.dkm.pets.entity.dto.PetsDto;
import com.dkm.pets.entity.dto.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhd
 * @date 2020/5/9 9:22
 */
@Repository
public interface PetsMapper extends BaseMapper<PetUserEntity> {
    /**
     * 获取宠物信息
     * @param userId
     * @return
     */
    List<PetsDto> findById(@Param("userId") Long userId,@Param("petId") Long petId);

    /**
     * 获取用户数据
     * @param userId
     * @return
     */
    @Select("SELECT user_info_grade,user_info_gold,user_info_renown FROM tb_user_info WHERE user_id =#{userId}")
    UserInfo findUserInfo(@Param("userId") Long userId);

    /**
     * 获取食物数据
     * @param userId
     * @return
     */
    @Select("SELECT f.*,d.food_name,d.food_url FROM (SELECT * FROM tb_food WHERE user_id =#{userId}) f LEFT JOIN tb_food_detail d ON f.food_id = d.food_id")
    List<FoodInfo> findFoodInfo(@Param("userId") Long userId);

    /***
     * 根据等级获取喂食规则
     * @param grade
     * @return
     */
    @Select("SELECT e.food_id,e.e_number,f.food_url,f.food_name FROM " +
            "(SELECT food_id,e_number FROM tb_pet_eat_food WHERE e_level = FLOOR( #{grade} / 3 )) e " +
            "LEFT JOIN tb_food_detail f ON e.food_id = f.food_id")
    List<EatFoodDto> findEatFood(@Param("grade") Integer grade);

    /**
     * 喂食操作改变用户金币、声望等
     * @param renown
     * @param userId
     * @return
     */
    @Update("update tb_user_info set user_info_renown = user_info_renown + #{renown} WHERE user_id = #{userId} ")
    Integer updateUserInfo(@Param("renown") Integer renown,@Param("userId") Long userId);

    /**
     * 喂食更新食物
     * @param number
     * @param foodId
     * @param userId
     * @return
     */
    @Update("UPDATE tb_food SET food_number = food_number - #{number} WHERE user_id = #{userId} AND food_id = #{foodId} and food_number >=#{number} ")
    Integer updateFoodInfo(@Param("number")Integer number,@Param("foodId") Long foodId,@Param("userId") Long userId);

    /**
     * 喂食更新进度
     * @param nowFood
     * @param pId
     * @param pGrade
     * @return
     */
    Integer updatePetInfo(@Param("nowFood") Integer nowFood,@Param("pGrade") Integer pGrade,@Param("pId")Long pId);

    /**
     * 获取宠物条数
     * @param userId
     * @return
     */
    @Select("SELECT COUNT(*) FROM `tb_pet_user` WHERE user_id = #{userId}")
    Integer selectPetsCounts(@Param("userId") Long userId);

}
