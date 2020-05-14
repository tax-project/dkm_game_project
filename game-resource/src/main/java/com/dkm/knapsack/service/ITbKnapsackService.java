package com.dkm.knapsack.service;


import com.dkm.knapsack.domain.TbKnapsack;

import java.util.List;

/**
 * <p>
 * 背包表 服务类
 * </p>
 *
 * @author zy
 * @since 2020-05-12
 */
public interface ITbKnapsackService {

    void addTbKnapsack(TbKnapsack tbKnapsack);

    List<TbKnapsack> findById(TbKnapsack TbKnapsack);
}
