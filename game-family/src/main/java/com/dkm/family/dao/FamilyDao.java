package com.dkm.family.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.family.entity.FamilyEntity;
import com.dkm.family.entity.vo.FamilyGoldInfoVo;
import com.dkm.family.entity.vo.FamilyImgsVo;
import com.dkm.family.entity.vo.HotFamilyVo;
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
            "SELECT family_id,user_id FROM tb_family_details WHERE is_admin=2) fd ON fd.family_id=f.family_id) f LEFT JOIN tb_user u ON f.user_id=u.user_id ")
    List<HotFamilyVo> getHotFamily();

    List<FamilyImgsVo> getImgs(@Param("param") List<Long> param);

    @Select("select family_id,family_name from tb_family")
    List<FamilyGoldInfoVo> selectFamilyGoldInfo();
}
