package com.dkm.mounts.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.mounts.entity.MountsInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhd
 * @date 2020/5/11 17:25
 */
@Repository
public interface MountsMapper extends IBaseMapper<MountsInfoEntity> {

    /**
     * 获取用户座驾
     * @param userId
     * @return
     */
    @Select("SELECT i.* FROM  (select mounts_id from tb_mounts WHERE user_id = #{userId}) m LEFT join  tb_mounts_info i  on i.mounts_id = m.mounts_id  ")
    List<MountsInfoEntity> haveMounts(@Param("userId") Long userId);
}
