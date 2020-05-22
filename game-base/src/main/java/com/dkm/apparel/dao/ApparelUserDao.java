package com.dkm.apparel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.apparel.entity.ApparelUserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @program: game_project
 * @description: 服饰关联
 * @author: zhd
 * @create: 2020-05-22 10:33
 **/
@Repository
public interface ApparelUserDao extends BaseMapper<ApparelUserEntity> {

    /**
     * 制作消费
     * @param gold
     * @return
     */
    @Update("update tb_user_info set user_info_gold = user_info_gold - #{gold}")
    Integer updateUser(@Param("gold") Integer gold);
}
