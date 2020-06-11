package com.dkm.shop.service;


import com.dkm.shop.domain.TbGrowth;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2020-06-11
 */
public interface ITbGrowthService  {
	public void insertTbGrowth(TbGrowth tbGrowth);
	int selectCountMy();
	int selectCountDj(TbGrowth tbGrowth);
}
