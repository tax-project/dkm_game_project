package com.dkm.diggings.service;


import com.dkm.diggings.bean.vo.DiggingsVo;
import com.dkm.diggings.bean.vo.MineDetailVo;
import com.dkm.diggings.bean.vo.OccupyResultVo;

/**
 * @author OpenE
 */
public interface IDiggingsService {
    /**
     * 得到矿区的所有信息
     *
     * @param userId   用户 ID
     * @param familyId 家族 ID
     * @return 矿区的全部信息
     */
    DiggingsVo getAllInfo(Long userId, Long familyId);



    /**
     * 占领一座矿山ID
     *
     * @param battleId 矿山ID
     * @return 占领的回执信息
     */
    OccupyResultVo occupy(long battleId, Long userId, Long familyId);


    /**
     * 查看矿山的详情信息
     *
     * @param battleId 矿山ID
     * @param userId
     * @return 详情信息
     */
    MineDetailVo detail(long battleId, Long userId);
}
