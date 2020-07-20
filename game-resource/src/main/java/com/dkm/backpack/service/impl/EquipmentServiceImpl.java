package com.dkm.backpack.service.impl;

import com.dkm.backpack.service.IEquipmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: game_project
 * @description: 装备信息
 * @author: zhd
 * @create: 2020-07-18 09:19
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class EquipmentServiceImpl implements IEquipmentService {

}
