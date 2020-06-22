package com.dkm.mounts.service;

import com.dkm.mounts.entity.MountsDetailEntity;
import com.dkm.mounts.entity.UserCenterMountsVo;
import com.dkm.mounts.entity.dto.MountsDetailDto;
import com.dkm.mounts.entity.dto.UserInfoDto;

import java.util.List;

/**
 * @author zhd
 * @date 2020/5/11 17:26
 */
public interface MountService {
    /**
     * 座驾列表
     * @return
     */
    List<MountsDetailDto> getAll(Long userId);

    /**
     * 拥有座驾
     * @param userId
     * @return
     */
    List<MountsDetailDto> haveMounts(Long userId);

    /**
     * 装备座驾
     * @param id
     * @param userId
     */
    void equipMount(Long id,Long userId);

    /**
     * 购买座驾
     * @param mountId
     * @param userId
     * @param gold
     * @param diamond
     */
    void buyMount(Long mountId,Long userId,Integer gold,Integer diamond);

    /**
     * 用户金币 钻石
     * @param userId
     * @return
     */
    UserInfoDto getUserInfo(Long userId);

    /**
     * 用户中心座驾信息
     * @param userId
     * @return
     */
    UserCenterMountsVo getUserCenterMounts(Long userId);
}
