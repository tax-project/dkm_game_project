package com.dkm.shop.service;


import com.dkm.shop.domain.TbCharge;

import java.util.List;

/**
 * <p>
 * 首充豪礼表 服务类
 * </p>
 *
 * @author zy
 * @since 2020-05-10
 */
public interface TbChargeService {

	List<TbCharge> selectAll();

	void addTbCharge(TbCharge tbCharges);
}
