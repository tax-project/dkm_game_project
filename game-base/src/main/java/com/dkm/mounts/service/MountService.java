package com.dkm.mounts.service;

import com.dkm.mounts.entity.MountsInfoEntity;

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
    List<MountsInfoEntity> getAll();

    /**
     * 拥有座驾
     * @param userId
     * @return
     */
    List<MountsInfoEntity> haveMounts(Long userId);
}
