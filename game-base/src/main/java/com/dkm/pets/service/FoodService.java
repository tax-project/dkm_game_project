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
<<<<<<< HEAD
     * 查询接口
     * @param userId 用户ID
     * @return 结果
=======
     * 背包食物
     * @param userId
     * @return
>>>>>>> b52471075ea16360371dc94fa5a6df20fc046b9f
     */
    List<BackPackFood> getPackFood(Long userId);

    /**
     * 出售食物
     * @return
     */
    Integer sellFood(Integer sellNumber,Long id);
}
