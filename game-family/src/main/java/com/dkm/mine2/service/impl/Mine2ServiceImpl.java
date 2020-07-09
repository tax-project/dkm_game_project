package com.dkm.mine2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.family.dao.FamilyDao;
import com.dkm.family.entity.FamilyEntity;
import com.dkm.mine2.bean.entity.MineBattleEntity;
import com.dkm.mine2.bean.entity.MineBattleItemEntity;
import com.dkm.mine2.bean.entity.MineBattleLevelEntity;
import com.dkm.mine2.bean.vo.*;
import com.dkm.mine2.dao.FamilyAdditionMapper;
import com.dkm.mine2.dao.MineBattleItemMapper;
import com.dkm.mine2.dao.MineBattleLevelMapper;
import com.dkm.mine2.dao.MineBattleMapper;
import com.dkm.mine2.rule.BattleItemRule;
import com.dkm.mine2.service.IMine2Service;
import com.dkm.utils.IdGenerator;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


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
    @Resource
    private MineBattleLevelMapper mineBattleLevelMapper;

    @Override
    public MineVo getAllInfo(Long userId, Long familyId) {
        MineBattleEntity entity = getMineBattleEntity(familyId);
        val result = new MineVo();
        result.setFamilyId(familyId);
        val locationId = entity2Vo(entity, result);
        includeMineItem(entity.getId(), result.getPublicItem(), 0);
        // 导入公开矿区信息
        includeMineItem(entity.getId(), result.getPrivateItem(), locationId);
        // 导入私有矿区信息
        result.setFamilyLevel(getFamilyLevel(familyId));
        return result;
    }


    @Resource
    private FamilyAdditionMapper mapper;

    @Override
    public List<FamilyAdditionVo2Entity> getFamilyType() {
        return mapper.selectList(null);
    }

    @Override
    public OccupyResultVo occupy(long battleId) {
        return null;
    }

    @Override
    public List<BattleItemPropVo> getItemsLevelType() {
        val entityList = mineBattleLevelMapper.selectList(null);
        val result = new ArrayList<BattleItemPropVo>(entityList.size());
        for (MineBattleLevelEntity levelEntity : entityList) {
            BattleItemPropVo battleItemPropVo = new BattleItemPropVo();
            battleItemPropVo.setNpcName(levelEntity.getNpcName());
            battleItemPropVo.setNpcSkillLevel(levelEntity.getNpcLevel());
            battleItemPropVo.setGoldYield(levelEntity.getGoldYield());
            battleItemPropVo.setIntegralYield(levelEntity.getIntegralYield());
            battleItemPropVo.setLevel(levelEntity.getLevel());
            result.add(battleItemPropVo);
        }
        return result;
    }

    private void includeMineItem(Long id, List<MineItemVo> publicItem, int i) {
        val itemEntities = mineBattleItemMapper.selectList(new QueryWrapper<MineBattleItemEntity>()
                .lambda().eq(MineBattleItemEntity::getBattleId, id).eq(MineBattleItemEntity::getBelongItem, i));
        for (int j = 0; j < itemEntities.size(); j++) {
            MineItemVo item = new MineItemVo();
            MineBattleItemEntity itemEntity = itemEntities.get(j);
            item.setId(itemEntity.getId());
            item.setIndex(j);
            item.setLevel(itemEntity.getLevel());
            publicItem.add(item);
        }
    }

    private int entity2Vo(MineBattleEntity entity, MineVo result) {
        long[] arr = new long[4];
        int resultId = -1;
        val longs = new ArrayList<Long>();
        arr[0] = entity.getFirstFamilyId();
        arr[1] = entity.getSecondFamilyId();
        arr[2] = entity.getThirdFamilyId();
        arr[3] = entity.getFourthFamilyId();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != result.getFamilyId()) {
                longs.add(arr[i]);
            } else {
                resultId = i + 1;
            }
        }
        if (longs.size() > 3) {
            throw new IndexOutOfBoundsException("数据异常");
        }

        result.setTopLeftFamilyId(longs.get(0));
        result.setTopRightFamilyId(longs.get(1));
        result.setBottomRightFamilyId(longs.get(2));
        return resultId;
    }

    /**
     * 得到金矿，如果家族不位于任何金矿时将自动添加，
     * 如果金矿不存在则将自动创建一个新的金矿
     */
    private MineBattleEntity getMineBattleEntity(Long familyId) {
        MineBattleEntity entity = mineBattleMapper.selectByFamilyId(familyId);
        if (entity == null) {
            entity = mineBattleMapper.selectByEmpty();
            if (entity == null) {
                entity = battleItemRule.createBattle();
            }
            entity.setFirstFamilyId(chooseFamilyExists(entity.getFirstFamilyId()));
            entity.setSecondFamilyId(chooseFamilyExists(entity.getSecondFamilyId()));
            entity.setThirdFamilyId(chooseFamilyExists(entity.getThirdFamilyId()));
            entity.setFourthFamilyId(chooseFamilyExists(entity.getFourthFamilyId()));
            putFamilyToBattle(entity, familyId);
            //战场不存在，需要生成
        }
        return entity;
    }

    /**
     * 判断家族是否存在，不存在返回 0
     */
    private long chooseFamilyExists(long firstFamilyId) {
        FamilyEntity familyEntity = familyDao.selectById(firstFamilyId);
        if (familyEntity == null) {
            return 0;
        } else {
            return firstFamilyId;
        }
    }

    /**
     * 将一个新家族添加到矿区
     */
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


    @Resource
    private FamilyDao familyDao;

    public Integer getFamilyLevel(Long familyId) {
        val familyEntity = familyDao.selectById(familyId);
        return familyEntity.getFamilyGrade();
    }
}
