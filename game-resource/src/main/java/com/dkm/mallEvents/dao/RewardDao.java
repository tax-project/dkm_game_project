package com.dkm.mallEvents.dao;

import com.dkm.mallEvents.entities.data.RechargeVo;
import com.dkm.mallEvents.entities.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RewardDao {

    RechargeVo selectInfoByTypeAndUserId(int i, Long userId);
}
