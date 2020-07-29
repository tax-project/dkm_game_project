package com.dkm.mallEvents.dao;

import com.dkm.mallEvents.entities.vo.*;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CancerDao {

    List<SingleTopUpVo> selectSingle();

    SingleUserVo findCheckedById(@Param("userId") Long userId, @Param("id") Integer id);

    List<GoodsInfoVo> selectById(Integer id);

    SingleHistoryUserVo selectHistoryByUser(Long userId, Integer id);

    void addUser(Long userId, Integer id);

    Prizes2StatusVo selectSomeWhere(Long userId, int i);
}
