package com.dkm.blackHouse.service;


import com.dkm.blackHouse.domain.TbBlackHouse;
import com.dkm.blackHouse.domain.vo.TbBlackHouseVo;

import java.util.List;

/**
 * <p>
 * 黑屋表 服务类
 * </p>
 *
 * @author zy
 * @since 2020-05-10
 */
public interface TbBlackHouseService  {

    int selectIsBlack(Long fromId);

    void insertLand(TbBlackHouse tbBlackHouse);

    void updateIsBlack(TbBlackHouse tbBlackHouse);

    TbBlackHouseVo selectIsBlackTwo(TbBlackHouse tbBlackHouse);

    List<TbBlackHouse> selectById();

    List<TbBlackHouse> selectById(Long userId);
}
