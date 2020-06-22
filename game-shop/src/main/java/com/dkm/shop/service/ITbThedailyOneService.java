package com.dkm.shop.service;


import com.dkm.shop.domain.TbThedailyOne;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2020-06-21
 */
public interface ITbThedailyOneService {
    int selectCountThd(String thdId);

    int selectCount(String thdId);

    void insert(String thdId);
}
