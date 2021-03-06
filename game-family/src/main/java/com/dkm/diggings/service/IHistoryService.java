package com.dkm.diggings.service;

import com.dkm.diggings.bean.entity.DiggingsHistoryEntity;
import com.dkm.diggings.bean.vo.FamilyExperienceVo;
import com.dkm.diggings.bean.vo.OccupiedVo;
import com.dkm.diggings.bean.vo.PersonalVo;
import com.dkm.utils.CollectionUtils;

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
     * @param id     主键 ID
     * @param itemId 矿区ID
     */
    void destroy(long id, Long itemId);

    /**
     * 创建一个新的历史记录
     *
     * @param userId
     * @param familyId
     * @param mineId
     */
    void createNewItem(Long userId, Long familyId, long mineId);

    Map<Long, OccupiedVo> selectUserOccupiedList(List<CollectionUtils.Pair<Long, Long>> collect, Long familyId);

    boolean expired(long mineId);

    /**
     * 判断金矿是否过期
     *
     * @param mineId
     * @param userId
     * @param familyId
     * @return
     */
    boolean expired(long mineId, Long userId, Long familyId);

    /**
     * 查询今天剩余的攻击次数
     *
     * @param userId
     * @return
     */
    int getOccupationSizeOnToday(Long userId);

    /**
     * 查询一天的积分贡献榜
     *
     * @param familyId 家族ID
     * @return
     */
    List<PersonalVo> getOneDayIntegral(Long familyId);

    /**
     * 查询整个积分贡献版
     *
     * @param familyId
     * @return
     */
    List<PersonalVo> getAllTheIntegral(Long familyId);

    List<FamilyExperienceVo> getDiggingsExperienceSort(Long familyId);
}
