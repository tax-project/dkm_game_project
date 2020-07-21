package com.dkm.diggings.service;

import com.dkm.diggings.bean.FamilyAddition;
import com.dkm.diggings.bean.vo.MineInfoVo;

import java.util.List;

/**
 * 静态的接口
 */
public interface IFamilyStaticService {
    /**
     * 得到矿山的等级映射关系
     *
     * @return 映射
     */
    List<MineInfoVo> getItemsLevelTypes();

    MineInfoVo getItemsLevelType(int level);

    /**
     * 得到家族的等级对应的加成以及段位名称
     *
     * @return 。
     */
    List<FamilyAddition> getFamilyType();

    /**
     * 获取用户技能等级
     *
     * @param userId 用户 ID
     */
    Integer getSkillLevel(Long userId);

}
