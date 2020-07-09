package com.dkm.diggings.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.diggings.bean.FamilyAddition;
import com.dkm.diggings.bean.entity.DiggingsEntity;
import com.dkm.diggings.bean.entity.MineEntity;
import com.dkm.diggings.bean.entity.MineLevelEntity;
import com.dkm.diggings.bean.vo.*;
import com.dkm.diggings.dao.DiggingsMapper;
import com.dkm.diggings.dao.FamilyAdditionMapper;
import com.dkm.diggings.dao.MineLevelMapper;
import com.dkm.diggings.dao.MineMapper;
import com.dkm.diggings.rule.MineRule;
import com.dkm.diggings.service.IDiggingsService;
import com.dkm.exception.ApplicationException;
import com.dkm.family.dao.FamilyDao;
import com.dkm.family.entity.FamilyEntity;
import com.dkm.utils.IdGenerator;
import com.dkm.utils.ObjectUtils;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author dragon
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DiggingsServiceImpl implements IDiggingsService {
    @Resource
    private IdGenerator idGenerator;
    @Resource
    private DiggingsMapper diggingsMapper;
    @Resource
    private MineMapper mineMapper;
    @Resource
    private MineRule mineRule;
    @Resource
    private MineLevelMapper mineLevelMapper;
    @Resource
    private FamilyDao familyDao;
    @Resource
    private FamilyAdditionMapper mapper;

    @Override
    public DiggingsVo getAllInfo(Long userId, Long familyId) {
        DiggingsEntity entity = getMineBattleEntity(familyId);
        val result = new DiggingsVo();
        result.setFamilyId(familyId);
        result.setFamilyName(loadFamilyName(familyId));
        val locationId = entity2Vo(entity, result);
        includeMineItem(entity.getId(), result.getPublicItem(), 0);
        // 导入公开矿区信息
        includeMineItem(entity.getId(), result.getPrivateItem(), locationId);
        // 导入私有矿区信息
        result.setFamilyLevel(getFamilyLevel(familyId));
        return result;
    }


    @Override
    public List<FamilyAddition> getFamilyType() {
        return mapper.selectList(null);
    }

    @Override
    public MineDetailVo detail(long battleId) {
        val item = mineMapper.selectById(battleId);
        if (ObjectUtils.isNullOrEmpty(item)) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "未找到此矿山.");
        }
        val result = new MineDetailVo();
        return result;
    }

    @Override
    public OccupyResultVo occupy(long battleId) {
        return null;
    }


    @Override
    public List<MineInfoVo> getItemsLevelType() {
        val entityList = mineLevelMapper.selectList(null);
        val result = new ArrayList<MineInfoVo>(entityList.size());
        for (MineLevelEntity levelEntity : entityList) {
            MineInfoVo mineInfoVo = new MineInfoVo();
            mineInfoVo.setNpcName(levelEntity.getNpcName());
            mineInfoVo.setNpcSkillLevel(levelEntity.getNpcLevel());
            mineInfoVo.setGoldYield(levelEntity.getGoldYield());
            mineInfoVo.setIntegralYield(levelEntity.getIntegralYield());
            mineInfoVo.setLevel(levelEntity.getLevel());
            result.add(mineInfoVo);
        }
        return result;
    }

    private void includeMineItem(Long id, List<MineVo> publicItem, int i) {
        val itemEntities = mineMapper.selectList(new QueryWrapper<MineEntity>()
                .lambda().eq(MineEntity::getBattleId, id).eq(MineEntity::getBelongItem, i));
        for (int j = 0; j < itemEntities.size(); j++) {
            MineVo item = new MineVo();
            MineEntity itemEntity = itemEntities.get(j);
            item.setId(itemEntity.getId());
            item.setIndex(j);
            item.setLevel(itemEntity.getItemLevel());
            publicItem.add(item);
        }
    }

    private int entity2Vo(DiggingsEntity entity, DiggingsVo result) {
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

        result.setTopRightFamilyName(loadFamilyName(longs.get(1)));
        result.setTopLeftFamilyName(loadFamilyName(longs.get(0)));
        result.setTopRightFamilyName(loadFamilyName(longs.get(1)));
        result.setBottomRightFamilyName(loadFamilyName(longs.get(2)));
        return resultId;
    }


    /**
     * 根据家族ID 来获取家族名称
     *
     * @deprecated sql查询可优化
     */
    @Deprecated
    private String loadFamilyName(Long familyId) {
        if (familyId == 0) {
            return "无";
        } else {
            return familyDao.selectNameByFamilyId(familyId);
        }
    }

    /**
     * 得到金矿，如果家族不位于任何金矿时将自动添加，
     * 如果金矿不存在则将自动创建一个新的金矿
     */
    private DiggingsEntity getMineBattleEntity(Long familyId) {
        DiggingsEntity entity = diggingsMapper.selectByFamilyId(familyId);
        if (Objects.isNull(entity)) {
            entity = diggingsMapper.selectByEmpty();
            if (Objects.isNull(entity)) {
                entity = mineRule.createNewBattle();
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
        if (Objects.isNull(familyEntity)) {
            return 0;
        } else {
            return firstFamilyId;
        }
    }

    /**
     * 将一个新家族添加到矿区
     */

    private void putFamilyToBattle(DiggingsEntity entity, Long familyId) {
        if (entity.getFirstFamilyId() == 0) {
            entity.setFirstFamilyId(familyId);
        } else if (entity.getSecondFamilyId() == 0) {
            entity.setSecondFamilyId(familyId);
        } else if (entity.getThirdFamilyId() == 0) {
            entity.setThirdFamilyId(familyId);
        } else if (entity.getFourthFamilyId() == 0) {
            entity.setFourthFamilyId(familyId);
        }
        diggingsMapper.updateById(entity);
    }


    public Integer getFamilyLevel(Long familyId) {
        val familyEntity = familyDao.selectById(familyId);
        return familyEntity.getFamilyGrade();
    }
}
