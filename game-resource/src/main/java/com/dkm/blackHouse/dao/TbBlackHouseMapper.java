package com.dkm.blackHouse.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.blackHouse.domain.TbBlackHouse;
import com.dkm.blackHouse.domain.vo.TbBlackHouseVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
  * 黑屋表 Mapper 接口
 * </p>
 *
 * @author zy
 * @since 2020-05-10
 */
@Mapper
public interface TbBlackHouseMapper extends BaseMapper<TbBlackHouse> {

    int selectIsBlack(Long fromId);

    int insertLand(List<TbBlackHouse> tbBlackHouse);

    TbBlackHouseVo selectIsBlackTwo(TbBlackHouse tbBlackHouse);

    TbBlackHouseVo selectIsBlackThree(TbBlackHouse tbBlackHouse);

}