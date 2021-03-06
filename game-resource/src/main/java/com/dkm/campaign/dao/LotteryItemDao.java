package com.dkm.campaign.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.campaign.entity.LotteryItemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LotteryItemDao extends BaseMapper<LotteryItemEntity> {

    List<LotteryItemEntity> selectAll(@Param("user") long userId);

    List<LotteryItemEntity> selectAllFull();
}
