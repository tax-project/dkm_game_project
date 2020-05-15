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
     * 查询接口
     * @param userId 用户ID
     * @return 结果
     */
    List<BackPackFood> getPackFood(Long userId);
}
