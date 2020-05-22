package com.dkm.apparel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.apparel.dao.ApparelMapper;
import com.dkm.apparel.dao.ApparelUserDao;
import com.dkm.apparel.entity.ApparelEntity;
import com.dkm.apparel.entity.ApparelUserEntity;
import com.dkm.apparel.service.ApparelService;
import com.dkm.utils.IdGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @program: game_project
 * @description: 服饰
 * @author: zhd
 * @create: 2020-05-19 19:22
 **/
@Service
public class ApparelServiceImpl implements ApparelService {
    @Resource
    private ApparelMapper apparelMapper;
    @Resource
    private ApparelUserDao apparelUserDao;
    @Resource
    private IdGenerator idGenerator;

    @Override
    public List<ApparelEntity> getAllApparels(Integer type) {
        if(type!=null){
           return apparelMapper.selectList(new QueryWrapper<ApparelEntity>().lambda().eq(ApparelEntity::getApparelType,type));
        }
        return apparelMapper.selectList(null);
    }

    @Override
    public List<ApparelEntity> getUserApparel(Long userId) {
        return apparelMapper.getUserApparel(userId);
    }

    @Override
    public void doApparel(Long apparelId,Long userId) {
        ApparelUserEntity apparelUserEntity = new ApparelUserEntity();
        apparelUserEntity.setApparelCompleteTime(LocalDateTime.now().minusHours(-12));
        apparelUserEntity.setUserId(userId);
        apparelUserEntity.setApparelDetailId(apparelId);
        apparelUserEntity.setApparelUserId(idGenerator.getNumberId());
        apparelUserDao.insert(apparelUserEntity);
        apparelUserDao.updateUser(2000);
    }
}
