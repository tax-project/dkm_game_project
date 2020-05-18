package com.dkm.knapsack.service.impl;


import com.dkm.knapsack.service.ITbEquipmentDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 装备详情 服务实现类
 * </p>
 *
 * @author zy
 * @since 2020-05-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TbEquipmentDetailsServiceImpl implements ITbEquipmentDetailsService {
	
}
