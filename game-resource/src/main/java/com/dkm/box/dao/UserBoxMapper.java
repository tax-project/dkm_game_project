package com.dkm.box.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.box.entity.UserBoxEntity;
import com.dkm.box.entity.vo.BoxInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description:
 * @author: zhd
 * @create: 2020-07-17 14:28
 **/
@Mapper
public interface UserBoxMapper extends IBaseMapper<UserBoxEntity> {

    @Select("SELECT box_level,box_id,open_time FROM tb_user_box WHERE user_id = #{userId} ORDER BY open_time,box_level")
    List<BoxInfoVo> selectBoxById(@Param("userId") Long userId);

    Integer insertList(@Param("list") List<UserBoxEntity> list);

    @Select("select user_info_grade from tb_user_info where user_id = #{userId}")
    Integer getUserGrade(@Param("userId") Long userId);

//    Integer updateBoxTime(@Param("list")List<UserBoxEntity> list);
    Integer updateBoxTime(@Param("date")LocalDateTime now);

}
