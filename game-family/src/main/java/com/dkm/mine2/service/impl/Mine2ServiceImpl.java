package com.dkm.mine2.service.impl;

import com.dkm.mine2.bean.entity.MineBattleEntity;
import com.dkm.mine2.bean.vo.AllMineInfoVo;
import com.dkm.mine2.dao.MineBattleItemMapper;
import com.dkm.mine2.dao.MineBattleMapper;
import com.dkm.mine2.rule.BattleItemRule;
import com.dkm.mine2.service.IMine2Service;
import com.dkm.utils.IdGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * @author dragon
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class Mine2ServiceImpl implements IMine2Service {
    @Resource
    private IdGenerator idGenerator;
    @Resource
    private MineBattleMapper mineBattleMapper;
    @Resource
    private MineBattleItemMapper mineBattleItemMapper;
    @Resource
    private BattleItemRule battleItemRule;

    @Override
    public AllMineInfoVo getAllInfo(Long userId, Long familyId) {
        MineBattleEntity entity = getMineBattleEntity(familyId);

        return null;
    }

    //获取矿产
    private MineBattleEntity getMineBattleEntity(Long familyId) {
        MineBattleEntity entity = mineBattleMapper.selectByFamilyId(familyId);
        if (entity != null) {
            return entity;
        } else {
            entity = mineBattleMapper.selectByEmpty();
            if (entity == null) {
                entity = battleItemRule.createBattle();
            }
            putFamilyToBattle(entity, familyId);
            return entity;
            //战场不存在，需要生成
        }
    }

    private void putFamilyToBattle(MineBattleEntity entity, Long familyId) {
        if (entity.getFirstFamilyId() == 0) {
            entity.setFirstFamilyId(familyId);
        } else if (entity.getSecondFamilyId() == 0) {
            entity.setSecondFamilyId(familyId);
        } else if (entity.getThirdFamilyId() == 0) {
            entity.setThirdFamilyId(familyId);
        } else if (entity.getFourthFamilyId() == 0) {
            entity.setFourthFamilyId(familyId);
        }
        mineBattleMapper.updateById(entity);
    }


}
