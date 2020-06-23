package com.dkm.medal.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dkm.medal.entity.MedalUserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @description: 用户勋章dao
 * @author: zhd
 * @create: 2020-06-06 11:26
 **/
@Component
public interface MedalUserDao extends IService<MedalUserEntity> {

    @Select("SELECT COUNT(*) FROM tb_medal_user WHERE user_id = #{userId} and medal_level>0")
    Integer getMedalCount(@Param("userId") Long userId);

}
