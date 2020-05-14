package com.dkm.turntable.service;

import com.dkm.turntable.entity.bo.TurntableItemBO;

import java.util.List;

/**
 * @Author: HuangJie
 * @Date: 2020/5/9 16:11
 * @Version: 1.0V
 */
public interface ITurntableService {
    /**
     * 获取转盘数据
     * @return 转盘数据
     */
    List<TurntableItemBO> luckyDrawItems();
}
