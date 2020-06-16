package com.dkm.familyMine.service;


/**
 * 战场初始化服务
 */
public interface IBattleFieldService {

    /**
     * 通过ID得到战场ID
     * @param familyId 家庭ID
     * @return 战场 ID
     */
    Long getBattleFieldByFamilyId(Long familyId);

    /**
     *
     * @param familyId
     * @return
     */
    Long createBattleFieldByFamilyId(Long familyId);
}
