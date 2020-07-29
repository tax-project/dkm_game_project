package com.dkm.mallEvents.dao;

import com.dkm.mallEvents.entities.vo.GoodsInfoVo;
import com.dkm.mallEvents.entities.vo.SingleHistoryUserVo;
import com.dkm.mallEvents.entities.vo.SingleTopUpVo;
import com.dkm.mallEvents.entities.vo.SingleUserVo;

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
}
