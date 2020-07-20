package com.dkm.backpack.service.impl;

import com.dkm.backpack.service.IGoodsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: game_project
 * @description: 物品实现
 * @author: zhd
 * @create: 2020-07-17 10:08
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsService implements IGoodsService {

}
