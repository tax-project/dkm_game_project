package com.dkm.family.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.family.entity.FamilyDetailEntity;
import com.dkm.family.entity.vo.FamilyUsersVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: game_project
 * @description: 家族详情
 * @author: zhd
 * @create: 2020-05-20 11:36
 **/
@Repository
public interface FamilyDetailDao extends BaseMapper<FamilyDetailEntity> {

    /**
     * 获取家族成员
     * @param familyId
     * @return
     */
    @Select("SELECT  fd.*,u.we_chat_nick_name,u.we_chat_head_img_url FROM (SELECT user_id,is_admin FROM tb_family_details WHERE family_id = #{familyId} ) fd LEFT JOIN tb_user u ON  fd.user_id=u.user_id ORDER BY is_admin DESC")
    List<FamilyUsersVo> selectFamilyUser(@Param("familyId") Long familyId);

    /**
     * 获取族长
     * @param familyId
     * @return
     */
    @Select("SELECT  fd.*,u.we_chat_nick_name,u.we_chat_head_img_url FROM (SELECT user_id,is_admin FROM tb_family_details WHERE family_id = #{familyId} and is_admin = 2) fd LEFT JOIN tb_user u ON  fd.user_id=u.user_id")
    FamilyUsersVo selectPatriarch(@Param("familyId") Long familyId);

    /**
     * 九张头像
     */
    @Select("SELECT we_chat_head_img_url FROM  (SELECT user_id FROM tb_family_details  WHERE family_id = #{familyId} ORDER BY is_admin DESC LIMIT 0,9) fd LEFT JOIN tb_user u on u.user_id=fd.user_id")
    List<String> getFamilyHeadImg(@Param("familyId") Long familyId);

    /**
     * 领取工资
     * @return
     */
    @Update("update tb_user_info set user_info_gold = user_info_gold + #{wage} where user_id = #{userId}")
    Integer updateUserWage(@Param("wage") Integer wage,@Param("userId") Long userId);
}
