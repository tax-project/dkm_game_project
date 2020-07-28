package com.dkm.mallEvents.service;

import com.dkm.mallEvents.entities.vo.SingleTopUpVo;

import java.util.List;

public interface ICancerService {
    List<SingleTopUpVo> getSingleTopUp(Long id);
}
