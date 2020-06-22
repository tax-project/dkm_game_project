package com.dkm.shop.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.shop.domain.TbThedailyOne;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author zy
 * @since 2020-06-21
 */
@Mapper
public interface TbThedailyOneMapper extends BaseMapper<TbThedailyOne> {
    int selectCountThd(TbThedailyOne tbThedailyOne);
}