package com.dkm.familyMine.service.impl;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.familyMine.dao.BattleFieldMapper;
import com.dkm.familyMine.dao.FamilyMineMapper;
import com.dkm.familyMine.entity.MineBattleFieldEntity;
import com.dkm.familyMine.service.IFamilyMineService;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
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
    private FamilyMineMapper mineDao;

    @Resource
    private BattleFieldMapper battleFieldMapper;

    /**
     * 通过家族ID 得到战场 ID
     *
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

    /**
     * 将家族添加到一个战场下
     *
     * @param familyId 家族ID
     * @return
     */
    private synchronized Long createBattleFieldByFamilyId(long familyId) {
        val id = idGenerator.getNumberId();
        var mineBattleFieldEntity = battleFieldMapper.selectEmptyMineBattle();
        if (mineBattleFieldEntity == null) {
            mineBattleFieldEntity = battleFieldMapper.createBattleFieldById(id);
        }
        battleFieldMapper.update(saveId(mineBattleFieldEntity, familyId));
        return id;
    }

    private MineBattleFieldEntity saveId(MineBattleFieldEntity fieldEntity, long familyId) {
        if (fieldEntity.getFamilyIdByFirst() == 0L) {
            fieldEntity.setFamilyIdByFirst(familyId);
        } else if (fieldEntity.getFamilyIdBySecond() == 0L) {
            fieldEntity.setFamilyIdBySecond(familyId);
        } else if (fieldEntity.getFamilyIdByThird() == 0L) {
            fieldEntity.setFamilyIdByThird(familyId);
        } else if (fieldEntity.getFamilyIdByForth() == 0L) {
            fieldEntity.setFamilyIdByForth(familyId);
        }else {
            throw new ApplicationException(CodeType.SERVICE_ERROR,"此对象被其他方法引用！");
        }
        return fieldEntity;
    }


}
