package com.dkm.shop.service;


import com.dkm.shop.domain.TbPrivilegeMall;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zy
 * @since 2020-06-09
 */
public interface ITbPrivilegeMallService {
	List<TbPrivilegeMall> selectAll();

	void insert(TbPrivilegeMall tbPrivilegeMall);
}
