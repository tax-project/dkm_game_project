package com.dkm.apparel.service;

import com.dkm.apparel.entity.ApparelEntity;

import java.util.List;

/**
 * @description: 服饰
 * @author: zhd
 * @create: 2020-05-19 19:21
 **/
public interface ApparelService {

    /**
     * 获取所有服饰
     * @return
     */
    List<ApparelEntity> getAllApparels(Integer type);

    /**
     * 获取用户服饰
     * @param userId
     * @return
     */
    List<ApparelEntity> getUserApparel(Long userId);

    /**
     * 制作服饰
     * @param apparelId
     */
    void doApparel(Long apparelId,Long userId);

}
