package com.dkm.family.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dkm.family.dao.FamilyLatelyDao;
import com.dkm.family.entity.FamilyLatelyEntity;
import com.dkm.family.service.IFamilyLatelyService;
import com.dkm.utils.IdGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-07-10 14:23
 **/
@Service
public class FamilyLatelyServiceImpl implements IFamilyLatelyService {
    @Resource
    private FamilyLatelyDao familyLatelyDao;
    @Resource
    private IdGenerator idGenerator;
    @Override
    public void add(Long userId, Long familyId) {
        List<FamilyLatelyEntity> familyLatelyEntities = familyLatelyDao.selectList(new LambdaQueryWrapper<FamilyLatelyEntity>()
                .eq(FamilyLatelyEntity::getUserId, userId).orderByDesc(FamilyLatelyEntity::getId));
        List<Long> ids = new ArrayList<>();
        boolean have = familyLatelyEntities.stream().noneMatch(a -> a.getFamilyId().equals(familyId));
        if(have&&familyLatelyEntities.size()>=3){
            for (int i = 2; i < familyLatelyEntities.size(); i++) {
                ids.add(familyLatelyEntities.get(i).getId());
            }
            familyLatelyDao.deleteBatchIds(ids);
        }
        if(have){
            FamilyLatelyEntity familyLatelyEntity = new FamilyLatelyEntity();
            familyLatelyEntity.setId(idGenerator.getNumberId());
            familyLatelyEntity.setFamilyId(familyId);
            familyLatelyEntity.setUserId(userId);
            familyLatelyDao.insert(familyLatelyEntity);
        }
    }
}
