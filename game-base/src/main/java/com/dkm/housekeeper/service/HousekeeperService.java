package com.dkm.housekeeper.service;

import com.dkm.housekeeper.entity.HousekeeperEntity;

import java.math.BigDecimal;
import java.util.Map;


/**
 * @author zhd
 * @date 2020/5/8 16:40
 */
public interface HousekeeperService {

    /**
     * 添加管家状态
     * @param userId
     * @param money
     * @return
     */
    int openHousekeeper(Long userId,BigDecimal money);

    /**
     * 管家剩余天数
     * @param userId
     * @return map
     */
    Map<String,Object> remnantDays(Long userId);

    /**
     * 修改管家时间信息
     * @param userId
     * @return
     */
    Map<String,String> updateTime(Long userId);
}
