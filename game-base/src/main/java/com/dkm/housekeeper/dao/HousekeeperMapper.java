package com.dkm.housekeeper.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.housekeeper.entity.HousekeeperEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhd
 * @date 2020/5/10 15:36
 */
@Repository
public interface HousekeeperMapper extends IBaseMapper<HousekeeperEntity> {

    /**
     * 获取宝箱id
     * @return
     */
    @Select("select box_id from tb_box where box_type = 1")
    List<Long> getAllBoxId();
}
