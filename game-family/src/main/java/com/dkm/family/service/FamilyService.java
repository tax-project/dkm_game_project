package com.dkm.family.service;

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
    void creatFamily(Long userId,String name);
}
