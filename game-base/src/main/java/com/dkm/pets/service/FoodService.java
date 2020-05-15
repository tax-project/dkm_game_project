package com.dkm.pets.service;

import com.dkm.pets.entity.dto.BackPackFood;

import java.util.List;

/**
 * @program: game_project
 * @description: 食物
 * @author: zhd
 * @create: 2020-05-15 11:32
 **/
public interface FoodService {
    /**
     * 背包食物
     * @param userId
     * @return
     */
    List<BackPackFood> getPackFood(Long userId);

    /**
     * 出售食物
     * @return
     */
    Integer sellFood(Integer sellNumber,Long id);
}
