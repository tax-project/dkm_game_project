package com.dkm.family.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.family.entity.FamilyDetailEntity;
import com.dkm.family.entity.vo.FamilyUsersVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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

    @Select("SELECT  fd.*,u.we_chat_nick_name,u.we_chat_head_img_url FROM (SELECT user_id,is_admin FROM tb_family_details WHERE family_id = #{familyId}) fd LEFT JOIN tb_user u ON  fd.user_id=u.user_id")
    List<FamilyUsersVo> selectFamilyUser(@Param("familyId") Long familyId);
}
