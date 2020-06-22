package com.dkm.shop.service;


import com.dkm.shop.domain.TbAccumulated;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2020-06-21
 */
public interface ITbAccumulatedService{
	List<TbAccumulated> selectAll();
}
