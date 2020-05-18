package com.dkm.pets.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.pets.entity.PetUserEntity;
import com.dkm.pets.entity.dto.PetsDto;
import com.dkm.pets.entity.dto.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
    @Select("SELECT pu.*,pd.pet_name,pd.pet_url FROM (SELECT * FROM tb_pet_user  WHERE user_id = #{userId}) pu LEFT JOIN tb_pet_detail pd on pd.pet_id = pu.pet_id")
    List<PetsDto> findById(@Param("userId") Long userId);

    /**
     * 获取用户数据
     * @param userId
     * @return
     */
    @Select("SELECT user_info_grade,user_info_gold,user_info_renown FROM tb_user_info WHERE user_id =#{userId}")
    UserInfo findUserInfo(@Param("userId") Long userId);

    /**
     * 批量插入宠物数据
     * @param list
     * @return
     */
    Integer insertList(List<PetUserEntity> list);

}
