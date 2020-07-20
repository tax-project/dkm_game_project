package com.dkm.campaign.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.campaign.entity.LotteryHistoryEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LotteryHistoryDao extends BaseMapper<LotteryHistoryEntity> {

    List<LotteryHistoryEntity> selectTenList();
}
