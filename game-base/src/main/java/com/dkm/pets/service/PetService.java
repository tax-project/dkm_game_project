package com.dkm.pets.service;

import com.dkm.pets.entity.dto.PetsDto;

import java.util.Map;

/**
 * @author zhd
 * @date 2020/5/9 9:17
 */
public interface PetService {
    /**
     * 获取宠物信息
     * @param userId
     * @return
     */
    Map<String,Object> getAllPets(Long userId);

    /**
     * 更新用户金币、声望
     * @param petInfo
     * @param userId
     * @return
     */
    Map<String,Object> feedPet(PetsDto petInfo, Long userId);

    /**
     * 升级宠物
     * @param pid
     * @param userId
     * @return
     */
    Map<String,Object> getNeedInfo(Long foodId,Long pid,Long userId);
}
