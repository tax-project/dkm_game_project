package com.dkm.gift.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.gift.dao.GiftDao;
import com.dkm.gift.entity.GiftEntity;
import com.dkm.gift.service.GiftService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-05-27 09:26
 **/
@Service
public class GiftServiceImpl implements GiftService {

    @Resource
    private GiftDao giftDao;

    @Override
    public List<GiftEntity> getAllGift(Integer type) {
        return giftDao.selectList(new QueryWrapper<GiftEntity>().lambda().eq(GiftEntity::getGiType,type));
    }
}
