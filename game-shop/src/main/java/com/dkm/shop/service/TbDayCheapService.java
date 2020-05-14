package com.dkm.shop.service;


import com.dkm.shop.domain.TbDayCheap;

import java.util.List;

/**
 * <p>
 * 每日特惠表 服务类
 * </p>
 *
 * @author zy
 * @since 2020-05-09
 */
public interface TbDayCheapService {
	List<TbDayCheap> selectAll();

	void addTbDayCheap(TbDayCheap tbDayCheap);
}
