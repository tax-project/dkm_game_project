package com.dkm.familyMine.service.impl;

import com.dkm.familyMine.dao.BattleFieldMapper;
import com.dkm.familyMine.service.IBattleFieldService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BattleFieldServiceImpl implements IBattleFieldService {

    @Resource
    private BattleFieldMapper fieldDao;

    @Override
    public Long getBattleFieldByFamilyId(Long familyId) {
        val fieldByFamilyId = fieldDao.getBattleFieldByFamilyId(familyId);
        if (fieldByFamilyId == null) {
            return createBattleFieldByFamilyId(familyId);
        }else {
            return fieldByFamilyId.getBattleFieldId();
        }
    }

    @Override
    public Long createBattleFieldByFamilyId(Long familyId) {
        fieldDao.createBattleFieldById(familyId);
        return familyId;
    }


}
