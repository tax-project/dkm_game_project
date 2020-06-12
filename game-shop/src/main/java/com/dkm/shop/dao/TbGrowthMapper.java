package com.dkm.shop.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.shop.domain.TbGrowth;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author zy
 * @since 2020-06-11
 */
@Mapper
public interface TbGrowthMapper extends BaseMapper<TbGrowth> {
    int selectCountMy(TbGrowth tbGrowth);

    int selectCountDj(TbGrowth tbGrowth);
}