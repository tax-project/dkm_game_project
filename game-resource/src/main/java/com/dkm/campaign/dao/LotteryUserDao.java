package com.dkm.campaign.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.campaign.entity.LotteryUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LotteryUserDao extends BaseMapper<LotteryUserEntity> {

    Integer insertAll(LotteryUserEntity[] array);

    Integer selectRemainingSize(@Param("lotteryId") Long lotteryId);
}
