package com.dkm.mine2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.mine2.bean.entity.MineBattleEntity;
import com.dkm.mine2.bean.entity.MineBattleItemEntity;
import com.dkm.mine2.bean.entity.MineBattleLevelEntity;
import com.dkm.mine2.bean.vo.BattleItemPropVo;
import com.dkm.mine2.bean.vo.MineInfoVo;
import com.dkm.mine2.bean.vo.MineItemInfoVo;
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
    public MineInfoVo getAllInfo(Long userId, Long familyId) {
        MineBattleEntity entity = getMineBattleEntity(familyId);
        val result = new MineInfoVo();
        result.setFamilyId(familyId);
        val locationId = entity2Vo(entity, result);
        includeMineItem(entity.getId(), result.getPublicItem(), 0);
        // 导入公开矿区信息
        includeMineItem(entity.getId(), result.getPrivateItem(), locationId);
        // 导入私有矿区信息
        return result;
    }

    @Override
    public List<BattleItemPropVo> getItemsLevelInfo() {
        val entityList = mineBattleLevelMapper.selectList(null);
        val result = new ArrayList<BattleItemPropVo>(entityList.size());
        for (MineBattleLevelEntity levelEntity : entityList) {
            BattleItemPropVo battleItemPropVo = new BattleItemPropVo();
            includeProp(levelEntity, battleItemPropVo);
            result.add(battleItemPropVo);
        }
        return result;
    }

    private void includeMineItem(Long id, List<MineItemInfoVo> publicItem, int i) {
        val itemEntities = mineBattleItemMapper.selectList(new QueryWrapper<MineBattleItemEntity>()
                .lambda().eq(MineBattleItemEntity::getBattleId, id).eq(MineBattleItemEntity::getBelongItem, i));
        for (int i1 = 0; i1 < itemEntities.size(); i1++) {
            MineItemInfoVo item = new MineItemInfoVo();
            MineBattleItemEntity itemEntity = itemEntities.get(i1);
            item.setId(itemEntity.getId());
            item.setIndex(i1);
            publicItem.add(item);
        }
    }

    private void includeProp(MineBattleLevelEntity mineBattleLevelEntity, BattleItemPropVo battleItemPropVo) {
        battleItemPropVo.setNpcName(mineBattleLevelEntity.getNpcName());
        battleItemPropVo.setNpcSkillLevel(mineBattleLevelEntity.getNpcLevel());
        battleItemPropVo.setGoldYield(mineBattleLevelEntity.getGoldYield());
        battleItemPropVo.setIntegralYield(mineBattleLevelEntity.getIntegralYield());
        battleItemPropVo.setLevel(mineBattleLevelEntity.getLevel());
    }

    /**
     * 莫得联合查询
     */
    private int entity2Vo(MineBattleEntity entity, MineInfoVo result) {
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
                resultId = i;
            }
        }
        if (longs.size() > 3) {
            throw new IndexOutOfBoundsException("数据异常");
        }

        result.setTopLeftFamilyId(longs.get(0));
        result.setTopRightFamilyId(longs.get(1));
        result.setBottomRightFamilyId(longs.get(2));
        return resultId ;
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
