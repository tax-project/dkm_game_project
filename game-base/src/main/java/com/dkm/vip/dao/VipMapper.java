package com.dkm.vip.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.vip.entity.VipEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author zhd
 * @date 2020/5/8 13:42
 */
@Repository
public interface VipMapper extends BaseMapper<VipEntity> {
    @Update("update tb_user set user_isvip = 1 where user_id = #{id}")
    Integer userAddVip(@Param("id") Long userId);
}
