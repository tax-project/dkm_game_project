package com.dkm.medal.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.medal.entity.MedalEntity;
import com.dkm.medal.entity.vo.MedalUserInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: dao
 * @author: zhd
 * @create: 2020-06-05 14:58
 **/
@Repository
public interface MedalDao extends BaseMapper<MedalEntity> {

    /**
     * 所有勋章信息
     * @param userId
     * @param type
     * @return
     */
    @Select("SELECT m.*,mu.medal_receive_count,IFNULL(mu.process,0) as process,IFNULL(mu.medal_level,0) as medal_level,g.gi_name FROM  ( " +
            "SELECT*FROM tb_medal WHERE medal_type=#{type}) m LEFT JOIN ( " +
            "SELECT medal_receive_count,process,medal_level,medal_id FROM tb_medal_user WHERE user_id=#{userId}) mu ON mu.medal_id=m.medal_id " +
            "LEFT JOIN tb_gift g on g.gi_id=m.gi_id")
    List<MedalUserInfoVo> selectUserMedal(@Param("userId") Long userId, @Param("type") Integer type);

    /**
     * 详细勋章页
     * @param userId
     * @param medalId
     * @return
     */
    @Select("SELECT m.*,mu.medal_receive_count,IFNULL(mu.process,0) as process,IFNULL(mu.medal_level,0) as medal_level,g.gi_name,g.gi_url as medalImage FROM  ( " +
            "SELECT*FROM tb_medal WHERE medal_id = #{medalId}) m LEFT JOIN ( " +
            "SELECT medal_receive_count,process,medal_level,medal_id FROM tb_medal_user WHERE user_id=#{userId}) mu ON mu.medal_id=m.medal_id " +
            "LEFT JOIN tb_gift g on g.gi_id=m.gi_id")
    MedalUserInfoVo selectOneUserMedal(@Param("userId") Long userId, @Param("medalId") Long medalId);

}
