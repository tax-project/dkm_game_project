package com.dkm.shop.service;


import com.dkm.shop.domain.TbWeeklyGiftBag;

import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2020-06-10
 */
public interface ITbWeeklyGiftBagService {
	List<TbWeeklyGiftBag> selectAll();
}
