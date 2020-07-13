package com.dkm.diggings.service;

import com.dkm.diggings.bean.entity.DiggingsHistoryEntity;

public interface IHistoryService {
    /**
     * 得到未完成的挖矿记录
     */
    DiggingsHistoryEntity getUnfinishedHistory(Long userId, Long familyId);

    /**
     * 得到最后一条挖矿记录
     */
    DiggingsHistoryEntity getLastHistory(Long userId, Long familyId);

    /**
     * 结算此记录
     *
     * @param id
     */
    void destroy(long id);
}
