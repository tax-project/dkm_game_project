package com.dkm.mallEvents.service;

import com.dkm.mallEvents.entities.data.RechargeVo;
import com.dkm.mallEvents.entities.vo.SingleTopUpVo;

import java.util.List;

public interface ICancerService {
    List<SingleTopUpVo> getSingleTopUp(Long id);

    Boolean getSingleTopUpInfoCheck(Long userId, Integer id);

    RechargeVo getCumulativeRecharge(Long id);
}
