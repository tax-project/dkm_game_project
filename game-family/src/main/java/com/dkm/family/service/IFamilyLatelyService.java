package com.dkm.family.service;

/**
 * @description:
 * @author: zhd
 * @create: 2020-07-10 14:23
 **/
public interface IFamilyLatelyService {

    /**
     * 添加访问记录
     * @param userId
     * @param familyId
     * @return
     */
    void add(Long userId,Long familyId);

}
