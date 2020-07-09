package com.dkm.diggings.service;


import com.dkm.diggings.bean.FamilyAddition;
import com.dkm.diggings.bean.vo.DiggingsVo;
import com.dkm.diggings.bean.vo.MineDetailVo;
import com.dkm.diggings.bean.vo.MineInfoVo;
import com.dkm.diggings.bean.vo.OccupyResultVo;

import java.util.List;

/**
 * @author OpenE
 */
public interface IMineService {
    /**
     * 得到矿区的所有信息
     *
     * @param userId   用户 ID
     * @param familyId 家族 ID
     * @return 矿区的全部信息
     */
    DiggingsVo getAllInfo(Long userId, Long familyId);


    /**
     * 得到矿山的等级映射关系
     *
     * @return 映射
     */
    List<MineInfoVo> getItemsLevelType();

    /**
     * 得到家族的等级对应的加成以及段位名称
     *
     * @return 。
     */
    List<FamilyAddition> getFamilyType();

    /**
     * 占领一座矿山ID
     *
     * @param battleId 矿山ID
     * @return 占领的回执信息
     */
    OccupyResultVo occupy(long battleId);


    /**
     * 查看矿山的详情信息
     *
     * @param battleId 矿山ID
     * @return 详情信息
     */
    MineDetailVo detail(long battleId);
}
