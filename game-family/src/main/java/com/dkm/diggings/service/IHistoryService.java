package com.dkm.diggings.service;

import com.dkm.diggings.bean.entity.DiggingsHistoryEntity;
import com.dkm.diggings.bean.other.Pair;
import com.dkm.diggings.bean.vo.OccupiedVo;

import java.util.List;
import java.util.Map;

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
     * @param itemId
     */
    void destroy(long id, Long itemId);

    void createNewItem(Long userId, Long familyId, long mineId);

    Map<Long, OccupiedVo> selectUserOccupiedList(List<Pair<Long, Long>> collect, Long familyId);
}
