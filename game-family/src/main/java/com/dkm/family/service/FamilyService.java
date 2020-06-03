package com.dkm.family.service;

import com.dkm.family.entity.FamilyEntity;

import java.util.List;
import java.util.Map;

/**
 * @description: 家族接口
 * @author: zhd
 * @create: 2020-05-20 10:50
 **/
public interface FamilyService {

    /**
     * 创建家族
     * @param userId
     * @param name
     */
    void creatFamily(Long userId, FamilyEntity name);

    /**
     * 获取家族详情
     * @param userId
     * @return
     */
    Map<String,Object> getFamilyInfo(Long userId);

    /**
     * 获取用户家族
     * @param userId
     * @return
     */
    FamilyEntity getMyFamily(Long userId);

    /**
     * 退出家族
     * @param UserId
     */
    void exitFamily(Long UserId);

    /**
     * 热门家族
     */
    List<FamilyEntity> getHotFamily();

    /**
     * 加入家族
     * @param userId
     * @param familyId
     * @return
     */
    void joinFamily(Long userId,Long familyId);

    /**
     * 设置管理员
     * @param setUserId
     * @param userId
     */
    void setAdmin(Long userId,Long setUserId);
}
