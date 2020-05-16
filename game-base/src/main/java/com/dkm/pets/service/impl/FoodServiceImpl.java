package com.dkm.pets.service.impl;

import com.dkm.pets.dao.FoodMapper;
import com.dkm.pets.entity.dto.BackPackFood;
import com.dkm.pets.service.FoodService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: game_project
 * @description: 食物实现类
 * @author: zhd
 * @create: 2020-05-15 11:33
 **/
@Service
public class FoodServiceImpl implements FoodService {
    @Resource
    private FoodMapper foodMapper;

    /**
     * 背包食物
     * @param userId
     * @return
     */
    @Override
    public List<BackPackFood> getPackFood(Long userId) {
        return foodMapper.getPackFood(userId);
    }

    /**
     * 出售食物
     * @return
     */
    @Override
    public Integer sellFood(Integer sellNumber,Long id) {
        return foodMapper.sellFood(sellNumber,id);
    }
}
