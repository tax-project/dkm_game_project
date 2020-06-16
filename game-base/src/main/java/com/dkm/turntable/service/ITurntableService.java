package com.dkm.turntable.service;

import com.dkm.turntable.entity.vo.TurntableInfoVo;

import java.util.List;

/**
 * @description: zhuanpan
 * @author: zhd
 * @create: 2020-06-11 09:57
 **/
public interface ITurntableService {

    /**
     * 获取转盘数据
     * @param userId
     * @param type
     * @return
     */
    List<TurntableInfoVo> getTurntable(Long userId, Integer type);
}
