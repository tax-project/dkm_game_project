package com.dkm.diggings.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.diggings.bean.entity.DiggingsEntity;
import com.dkm.diggings.bean.entity.MineEntity;
import com.dkm.diggings.bean.other.Pair;
import com.dkm.diggings.bean.vo.DiggingsVo;
import com.dkm.diggings.bean.vo.MineDetailVo;
import com.dkm.diggings.bean.vo.MineVo;
import com.dkm.diggings.bean.vo.OccupiedVo;
import com.dkm.diggings.dao.DiggingsMapper;
import com.dkm.diggings.dao.MineMapper;
import com.dkm.diggings.rule.MineRule;
import com.dkm.diggings.service.IDiggingsService;
import com.dkm.diggings.service.IHistoryService;
import com.dkm.diggings.service.IOccupiedService;
import com.dkm.diggings.service.IStaticService;
import com.dkm.exception.ApplicationException;
import com.dkm.family.dao.FamilyDao;
import com.dkm.family.entity.FamilyEntity;
import com.dkm.utils.IdGenerator;
import com.dkm.utils.ObjectUtils;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author dragon
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
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
    private FamilyDao familyDao;
    @Resource
    private IStaticService staticService;
    @Resource
    private IHistoryService historyService;
    @Resource
    private IOccupiedService occupiedService;

    public DiggingsServiceImpl() {
    }

    @Override
    public DiggingsVo getAllInfo(Long userId, Long familyId) {
        DiggingsEntity entity = getMineBattleEntity(familyId);
        val result = new DiggingsVo();
        result.setFamilyId(familyId);
        result.setFamilyName(loadFamilyName(familyId));
        val locationId = entity2Vo(entity, result);
        includeMineItem(entity.getId(), result.getPublicItem(), 0, userId, familyId);
        // 导入公开矿区信息
        includeMineItem(entity.getId(), result.getPrivateItem(), locationId, userId, familyId);
        // 导入私有矿区信息
        result.setFamilyLevel(getFamilyLevel(familyId));
        return result;
    }


    @Deprecated
    @Override
    public MineDetailVo detail(long battleId, Long userId) {
        val item = mineMapper.selectById(battleId);
        if (ObjectUtils.isNullOrEmpty(item)) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "未找到此矿山.");
        }
        val result = new MineDetailVo();
        result.setInfo(staticService.getItemsLevelTypes().get(item.getItemLevel()));
        if (item.getUserId() == 0) {
            // 如果矿区未被占领
            result.setHerSkillLevel(result.getInfo().getLevel());
            result.setHerName(result.getInfo().getNpcName());
            String name;
            switch (LocalDate.now().getDayOfWeek()) {
                case MONDAY:
                    name = "顶猪头";
                    break;
                case TUESDAY:
                    name = "胡言乱语";
                    break;
                case WEDNESDAY:
                    name = "顺手牵羊";
                    break;
                case THURSDAY:
                    name = "踢出家族";
                    break;
                case FRIDAY:
                    name = "禁言";
                    break;
                case SATURDAY:
                    name = "幸运之吻";
                    break;
                case SUNDAY:
                    name = "声望";
                    break;
                default:
                    throw new ApplicationException(CodeType.SERVICE_ERROR, "出现不可能发生的错误！");
            }
            result.setSkillName(name);
            val userSkillLevel = staticService.getSkillLevel(userId);
            result.setSkillLevel(userSkillLevel);
            val info = staticService.getItemsLevelTypes().get(item.getItemLevel());
            val herUserId = item.getUserId();
            val successRate = mineRule.calculateSuccessRate(userSkillLevel, herUserId == 0 ? info.getNpcSkillLevel() : staticService.getSkillLevel(herUserId));
            result.setSuccessRate(successRate);
        }
        return result;
    }


    private void includeMineItem(Long id, List<MineVo> publicItem, int location, Long userId, Long familyId) {
        val itemEntities = mineMapper.selectList(new QueryWrapper<MineEntity>()
                .lambda().eq(MineEntity::getBattleId, id).eq(MineEntity::getBelongItem, location));
        Map<Long, MineVo> map = new LinkedHashMap<>(itemEntities.size());
        for (int j = 0; j < itemEntities.size(); j++) {
            MineVo item = new MineVo();
            MineEntity itemEntity = itemEntities.get(j);
            item.setId(itemEntity.getId());
            item.setIndex(j);
            item.setLevel(itemEntity.getItemLevel());
            if (itemEntity.getUserId() != 0) {
                map.put(itemEntity.getUserId(), item);
            } else {
                publicItem.add(item);
            }
        }
        final List<Pair<Long, Long>> collect = itemEntities.stream().filter(k -> k.getUserId() != 0)
                .map(t -> new Pair<>(t.getId(), t.getUserId())).collect(Collectors.toList());
        Map<Long, OccupiedVo> occupiedVoMap = historyService.selectUserOccupiedList(collect, familyId);
        if (occupiedVoMap != null && occupiedVoMap.size() > 0 && map.size() > 0) {
            occupiedVoMap.forEach((k, v) -> map.get(k).setOccupiedInfo(v));
            publicItem.addAll(map.values());
        }
        publicItem.sort((j, k) -> (int)
                (j.getId() - k.getId()));
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
