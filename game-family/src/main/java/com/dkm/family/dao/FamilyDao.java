package com.dkm.family.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.family.entity.FamilyEntity;
import com.dkm.family.entity.vo.FamilyGoldInfoVo;
import com.dkm.family.entity.vo.FamilyImgsVo;
import com.dkm.family.entity.vo.HotFamilyVo;
import com.dkm.family.entity.vo.UserInfoVo;
import com.dkm.union.entity.vo.UnionFamilyInfoVo;
import org.apache.ibatis.annotations.Param;
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
    //性能好的随机查询
//    @Select("SELECT f.*,u.we_chat_head_img_url as headImg FROM (" +
//            "SELECT f.*,fd.user_id FROM (" +
//            "SELECT * FROM tb_family WHERE family_id>=((" +
//            "SELECT max(family_id) FROM tb_family)-(" +
//            "SELECT min(family_id) FROM tb_family))*RAND()+(" +
//            "SELECT MIN(family_id) FROM tb_family) LIMIT 10) f LEFT JOIN (" +
//            "SELECT family_id,user_id FROM tb_family_details WHERE is_admin=2) fd ON fd.family_id=f.family_id) f LEFT JOIN tb_user u ON f.user_id=u.user_id ")

    //性能不好的---进行了全表扫描
    @Select("SELECT f.*,u.we_chat_head_img_url as headImg FROM ( " +
            "SELECT f.*,fd.user_id FROM ( " +
            "SELECT * FROM tb_family ORDER BY family_user_number LIMIT 10) f LEFT JOIN ( " +
            "SELECT family_id,user_id FROM tb_family_details WHERE is_admin=2) fd ON fd.family_id=f.family_id) f LEFT JOIN tb_user u ON f.user_id=u.user_id where f.user_id != #{userId} ")
    List<HotFamilyVo> getHotFamily(@Param("userId")Long userId);

    List<HotFamilyVo> getLatelyFamily(@Param("ids") List<Long> ids,@Param("userId")Long userId);

    @Select("SELECT family_name FROM tb_family WHERE family_id = #{family_id}")
    String selectNameByFamilyId(@Param("family_id") long familyId);

    List<FamilyImgsVo> getImgs(@Param("param") List<Long> param);

    @Select("select family_id,family_name from tb_family")
    List<FamilyGoldInfoVo> selectFamilyGoldInfo();

    @Select("SELECT family_id,family_user_number,family_name,family_introduce,family_welcome_words FROM tb_family WHERE union_id = #{unionId}")
    List<UnionFamilyInfoVo> getUnionFamily(@Param("unionId") Long unionId);



    @Select("SELECT we_chat_nick_name,we_chat_head_img_url from tb_user WHERE user_id = #{userId}")
    UserInfoVo getUserinfoById(@Param("userId")Long userId);
}
