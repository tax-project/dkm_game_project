package com.dkm.shop.service;


import com.dkm.shop.domain.TbShops;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author zy
 * @since 2020-05-09
 */
public interface TbShopsService{
    List<TbShops> selectAll();

    void addTbShops(TbShops tbShops);
}
