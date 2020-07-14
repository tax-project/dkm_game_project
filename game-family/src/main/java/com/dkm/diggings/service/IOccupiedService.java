package com.dkm.diggings.service;


import com.dkm.diggings.bean.vo.OccupyResultVo;

/**
 * @author dragon
 */
public interface IOccupiedService {
    /**
     * 占领一座矿山ID
     *
     * @param battleId 矿山ID
     * @return 占领的回执信息
     */
    OccupyResultVo occupy(long battleId, Long userId, Long familyId);

    /**
     * 占领
     */
    OccupyResultVo disOccupy(long mineId, Long userId, Long familyId);
}
