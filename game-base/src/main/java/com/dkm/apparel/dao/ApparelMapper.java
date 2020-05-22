package com.dkm.apparel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.apparel.entity.ApparelEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: 服饰
 * @author: zhd
 * @create: 2020-05-15 14:03
 **/
@Repository
public interface ApparelMapper extends BaseMapper<ApparelEntity> {

    /**
     * 获取用户服饰
     * @param userId
     * @param type
     * @return
     */
    List<ApparelEntity> getUserApparel(@Param("userId")Long userId, @Param("type")Integer type);


}
