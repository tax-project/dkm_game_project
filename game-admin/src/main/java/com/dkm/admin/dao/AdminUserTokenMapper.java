package com.dkm.admin.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.admin.mapper.bo.AdminUserTokenEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author fkl
 */
@Repository
public interface AdminUserTokenMapper extends BaseMapper<AdminUserTokenEntity> {

    @Select("select * from tb_admin_user_token where token_id = #{token}")
    AdminUserTokenEntity findItemByToken(@Param("token") String token);
}
