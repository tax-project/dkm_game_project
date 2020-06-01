package com.dkm.apparel.service;

import com.dkm.apparel.entity.ApparelEntity;
import com.dkm.apparel.entity.dto.ApparelDto;

import java.util.List;

/**
 * @description: 服饰
 * @author: zhd
 * @create: 2020-05-19 19:21
 **/
public interface ApparelService {

    /**
     * 获取所有服饰
     * @param type
     * @param userId
     * @return
     */
    List<ApparelEntity> getAllApparels(Integer type,Long userId);

    /**
     * 获取用户服饰
     * @param userId
     * @return
     */
    List<ApparelEntity> getUserApparel(Long userId,Integer type);

    /**
     * 制作服饰
     * @param apparelId
     * @param userId
     */
    void doApparel(Long apparelId,Long userId);

    /**
     *  获取正在制作的服饰
     * @param userId
     * @return
     */
    ApparelDto getDoing(Long userId);

    /**
     * 立即制作完成
     * @param userId
     * @param apparelId
     */
    void nowDoing(Long userId,Long apparelId,Integer diamond);

    /**
     * 装备服饰
     * @param userId
     * @param apparelId
     * @param type
     */
    void equipApparel(Long userId,Long apparelId,Integer type);

    /**
     * 获取装备中的服饰
     * @param userId
     * @return
     */
    List<ApparelDto> getEquip(Long userId);


    /**
     * 出售服饰
     * @param apparelUserId
     * @param userId
     */
    void sellApparel(Long apparelUserId,Long userId);
}
