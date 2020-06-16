package com.dkm.turntable.service;

import com.dkm.turntable.entity.TurntableCouponEntity;

import java.util.List;
import java.util.Map;

/**
 * @description: 转盘券
 * @author: zhd
 * @create: 2020-06-11 15:00
 **/
public interface ITurntableCouponService {

    /**
     * 获取用户优惠券信息
     * @param userId
     * @return
     */
    Map<String,Object> getUserCoupon(Long userId);


}
