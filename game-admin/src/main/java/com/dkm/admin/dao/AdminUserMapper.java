package com.dkm.admin.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.admin.mapper.bo.AdminUserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fkl
 */
@Repository
public interface AdminUserMapper extends BaseMapper<AdminUserEntity> {

    @Select("select * from tb_admin_user")
    List<AdminUserEntity> selectAll();

    @Select("select * from tb_admin_user where user_name = #{userName}")
    AdminUserEntity selectAllByUserName(@Param("userName") String userName);

}
