package com.dkm.pets.service;

import com.dkm.pets.entity.dto.PetsDto;
import com.dkm.pets.entity.vo.FeedPetInfoVo;

import java.util.List;
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
     * 喂食
     */
    void feedPet(FeedPetInfoVo petInfoVo);

    /**
     * 升级
     */
    void petLevelUp(FeedPetInfoVo petInfoVo);

    /**
     * 获取宠物（内部）
     * @param userId
     * @return
     */
    List<PetsDto> getPetInfo(Long userId);

    String isHunger(Long userId);
}
