package com.dkm.knapsack.service;


import com.dkm.knapsack.domain.TbKnapsack;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2020-05-14
 */
public interface ITbKnapsackService{
    void updateByPrimaryKeySelective(TbKnapsack tbKnapsack);

    void addTbKnapsack(TbKnapsack tbKnapsack);

    List<TbKnapsack> findById(TbKnapsack TbKnapsack);

    TbKnapsack selectByIdTwo(Long userId);
}
