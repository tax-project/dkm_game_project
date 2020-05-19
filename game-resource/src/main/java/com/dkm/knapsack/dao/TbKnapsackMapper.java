package com.dkm.knapsack.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.knapsack.domain.TbKnapsack;
import org.springframework.stereotype.Service;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author zy
 * @since 2020-05-14
 */
@Service
public interface TbKnapsackMapper extends BaseMapper<TbKnapsack> {
    int updateByPrimaryKeySelective(TbKnapsack tbKnapsack);
}