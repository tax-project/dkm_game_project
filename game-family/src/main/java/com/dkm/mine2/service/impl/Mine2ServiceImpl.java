package com.dkm.mine2.service.impl;

import com.dkm.mine2.bean.entity.MineBattleEntity;
import com.dkm.mine2.bean.vo.AllMineInfoVo;
import com.dkm.mine2.dao.MineBattleItemMapper;
import com.dkm.mine2.dao.MineBattleMapper;
import com.dkm.mine2.rule.BattleItemRule;
import com.dkm.mine2.service.IMine2Service;
import com.dkm.utils.IdGenerator;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;


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
        val result = new AllMineInfoVo();
        result.setFamilyId(familyId);
        entity2Vo(entity, result);
        return result;
    }


    private void entity2Vo(MineBattleEntity entity, AllMineInfoVo result) {
        long[] arr = new long[4];
        val longs = new ArrayList<Long>();
        arr[0] = entity.getFirstFamilyId();
        arr[1] = entity.getSecondFamilyId();
        arr[2] = entity.getThirdFamilyId();
        arr[3] = entity.getFourthFamilyId();
        for (long l : arr) {
            if (l != result.getFamilyId()) {
                longs.add(l);
            }
        }
        if (longs.size() > 3) {
            throw new IndexOutOfBoundsException("数据异常");
        }
        result.setTopLeftFamilyId(longs.get(0));
        result.setTopRightFamilyId(longs.get(1));
        result.setBottomRightFamilyId(longs.get(2));
    }

    //获取矿产
    private MineBattleEntity getMineBattleEntity(Long familyId) {
        MineBattleEntity entity = mineBattleMapper.selectByFamilyId(familyId);
        if (entity == null) {
            entity = mineBattleMapper.selectByEmpty();
            if (entity == null) {
                entity = battleItemRule.createBattle();
            }
            putFamilyToBattle(entity, familyId);
            //战场不存在，需要生成
        }
        return entity;
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
