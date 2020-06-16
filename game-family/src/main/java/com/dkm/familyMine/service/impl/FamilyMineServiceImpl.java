package com.dkm.familyMine.service.impl;

import com.dkm.familyMine.dao.FamilyMineDao;
import com.dkm.familyMine.entity.MineEntity;
import com.dkm.familyMine.service.IFamilyMineService;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class FamilyMineServiceImpl implements IFamilyMineService {

    @Autowired
    private IdGenerator idGenerator;
    @Resource
    private FamilyMineDao mineDao;

    /**
     * 通过家族ID 得到战场 ID
     * @param id 家族ID
     * @return 战场 ID
     */
    @Override
    public Long getMineBattleFieldId(long id) {
        val familyMineById = mineDao.getFamilyMineById(id);
        if (familyMineById.size() == 0) {
            return createBattleFieldByFamilyId(id);
        }
        return familyMineById.get(0).getBattleFieldId();
    }

    @Override
    public Long createBattleFieldByFamilyId(long familyId) {

        return null;
    }


}
