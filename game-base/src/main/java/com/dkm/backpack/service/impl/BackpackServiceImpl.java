package com.dkm.backpack.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.backpack.dao.BackpackMapper;
import com.dkm.backpack.entity.Backpack;
import com.dkm.backpack.service.IBackpackService;
import org.springframework.stereotype.Service;

/**
 * @Author: HuangJie
 * @Date: 2020/5/14 15:12
 * @Version: 1.0V
 */
@Service
public class BackpackServiceImpl extends ServiceImpl<BackpackMapper, Backpack> implements IBackpackService {

    private final static String FOOD_TABLE = "";
    @Override
    public void addBackpackItem(Long itemId, String itemTableName) {

    }
}