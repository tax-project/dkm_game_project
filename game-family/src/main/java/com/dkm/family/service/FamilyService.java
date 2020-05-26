package com.dkm.family.service;

import com.dkm.family.entity.FamilyEntity;

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
     * @param familyId
     * @return
     */
    Map<String,Object> getFamilyInfo(Long familyId);

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

    void getHotFamily();
}
