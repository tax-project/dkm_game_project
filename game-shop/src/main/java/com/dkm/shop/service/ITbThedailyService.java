package com.dkm.shop.service;


import com.dkm.shop.domain.TbThedaily;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2020-06-21
 */
public interface ITbThedailyService{
	List<TbThedaily> selectAll();

	List<TbThedaily> findById(Long thdId);
}
