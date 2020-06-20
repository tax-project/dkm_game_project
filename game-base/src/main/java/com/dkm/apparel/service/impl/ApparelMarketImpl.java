package com.dkm.apparel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dkm.apparel.dao.ApparelMapper;
import com.dkm.apparel.dao.ApparelMarketMapper;
import com.dkm.apparel.dao.ApparelUserDao;
import com.dkm.apparel.entity.ApparelUserEntity;
import com.dkm.apparel.service.IApparelMarketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-06-19 15:28
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class ApparelMarketImpl implements IApparelMarketService {

    @Resource
    private ApparelMapper apparelMapper;

    @Resource
    private ApparelUserDao apparelUserDao;

    @Resource
    private ApparelMarketMapper apparelMarketMapper;

    @Override
    public void putOnSell(Long userId, Long apparelId) {
        ApparelUserEntity apparelUserEntity = apparelUserDao.selectOne(new LambdaQueryWrapper<ApparelUserEntity>().eq(ApparelUserEntity::getUserId, userId).eq(ApparelUserEntity::getApparelDetailId, apparelId));
        if(apparelUserEntity==null){

        }
    }
}
