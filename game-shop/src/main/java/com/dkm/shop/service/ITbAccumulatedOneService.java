package com.dkm.shop.service;



/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2020-06-21
 */
public interface ITbAccumulatedOneService {

    void insert(String talId);

    int selectCount(String talId);
}
