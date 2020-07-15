package com.dkm.diggings.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.diggings.bean.entity.DiggingsHistoryEntity;
import com.dkm.diggings.bean.other.Pair;
import com.dkm.diggings.bean.vo.OccupiedVo;
import com.dkm.diggings.bean.vo.UserInfoBO;
import com.dkm.diggings.dao.DiggingsHistoryMapper;
import com.dkm.diggings.dao.MineMapper;
import com.dkm.diggings.rule.MineRule;
import com.dkm.diggings.service.IHistoryService;
import com.dkm.diggings.service.IOccupiedService;
import com.dkm.diggings.service.IStaticService;
import com.dkm.exception.ApplicationException;
import com.dkm.family.dao.FamilyDao;
import com.dkm.family.entity.FamilyEntity;
import com.dkm.feign.UserFeignClient;
import com.dkm.utils.DateUtils;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 矿区历史记录以及处理
 *
 * @author dragon
 */
@Slf4j
@Service
public class HistoryServiceImpl implements IHistoryService {
    @Resource
    private DiggingsHistoryMapper historyMapper;
    @Resource
    private IOccupiedService occupiedService;
    @Resource
    private IStaticService staticService;
    @Resource
    private MineMapper mineMapper;
    @Resource
    private IdGenerator idGenerator;
    @Resource
    private MineRule rule;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserFeignClient userFeignClient;
    @Resource
    private FamilyDao familyDao;

    @Override
    public int getOccupationSizeOnToday(Long userId) {
        final List<DiggingsHistoryEntity> historyEntities = getDiggingsHistoryEntitiesOnToday(userId);
        return historyEntities.size();
    }

    private List<DiggingsHistoryEntity> getDiggingsHistoryEntitiesOnToday(Long userId) {
        final val now = LocalDateTime.now();
        final val today = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0);
        return historyMapper.selectList(new QueryWrapper<DiggingsHistoryEntity>().lambda()
                .eq(DiggingsHistoryEntity::getUserId, userId).gt(DiggingsHistoryEntity::getStartDate, today));
    }

    @Override
    public DiggingsHistoryEntity getUnfinishedHistory(Long userId, Long familyId) {
        val now = LocalDateTime.now();
        val lastHistory = getLastHistory(userId, familyId);
        val today = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0);
        if (lastHistory == null) {
            return null;
        } else {
            if (lastHistory.getStopDate().isAfter(now)) {
                if (lastHistory.isSettled()) {
                    return null;
                }
                return lastHistory;
            } else {
                if (!lastHistory.isSettled()) {
                    return lastHistory;
                }
                return null;
            }
        }
    }

    @Override
    public DiggingsHistoryEntity getLastHistory(Long userId, Long familyId) {
        return historyMapper.selectLastOneByUserIdAndFamilyId(userId, familyId);
    }

    @Override
    public void destroy(long id, Long mineId) {
        val now = LocalDateTime.now();
        val entity = historyMapper.selectById(id);
        if (entity == null) {
            log.error("此条历史记录[{}]无法查询，注意.", id);
            throw new ApplicationException(CodeType.SERVICE_ERROR, "查询出错");
        }
        if (entity.isSettled()) {
            return;
        }
        entity.setSettled(true);
        val startDate = entity.getStartDate();
        long dateSize = rule.getDateSizeMinutes(now, startDate);
        entity.setStopDate(now);
        historyMapper.updateById(entity);
        flushData(entity.getMineItemLevel(), entity.getUserId(), mineId, dateSize);
    }


    private void flushData(int mineItemLevel, long userId, long mineId, long dateSize) {
        val mineEntity = mineMapper.selectById(mineId);
        if (Objects.isNull(mineEntity)) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "未知错误，家族可能被解散导致矿区不存在");
        }
        if (mineEntity.getUserId() == userId) {
            mineEntity.setUserId(0);
        }
        val levelType = staticService.getItemsLevelType(mineItemLevel);
        // 计算如果挖矿时间不大于60的情况
        val userInfoBO = new UserInfoBO();
        userInfoBO.setUserId(userId);
        val goldYield = levelType.getGoldYield();
        userInfoBO.setUserInfoGold(rule.chooseGoldOrIntegralYield(dateSize, goldYield));
        val integralYield = levelType.getIntegralYield();
        userInfoBO.setUserInfoNowExperience(rule.chooseGoldOrIntegralYield(dateSize, integralYield));
        userFeignClient.update(userInfoBO);
        mineEntity.setUserId(0);
        mineEntity.setFamilyId(0);
        mineMapper.updateById(mineEntity);
    }


    @Override
    public boolean expired(long mineId, Long userId, Long familyId) {
        final val lastHistory = getLastHistory(userId, familyId);
        if (lastHistory != null) {
            if (rule.getDateSizeMinutes(LocalDateTime.now(), lastHistory.getStartDate()) >= MineRule.DATE_LEN_MINUTES) {
                destroy(lastHistory.getId(), mineId);
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<Long, OccupiedVo> selectUserOccupiedList(List<Pair<Long, Long>> collect, Long familyId) {
        final val now = LocalDateTime.now();
        final List<DiggingsHistoryEntity> diggingsHistoryEntities = historyMapper.selectAccept(collect, familyId, now);
        Map<Long, OccupiedVo> map = new HashMap<>(diggingsHistoryEntities.size());
        for (DiggingsHistoryEntity entity : diggingsHistoryEntities) {
            final OccupiedVo value = new OccupiedVo();
            final val data = userFeignClient.queryUser(entity.getUserId()).getData();
            value.setUserName(data.getWeChatNickName());
            final val familyEntity = familyDao.selectOne(new QueryWrapper<FamilyEntity>().lambda().eq(FamilyEntity::getFamilyId, entity.getFamilyId()));
            if (familyEntity == null) {
                throw new ApplicationException(CodeType.SERVICE_ERROR, "错误，未找到家族");
            }
            value.setUserFamilyName(familyEntity.getFamilyName());
            value.setStopDate(DateUtils.formatDateTime(entity.getStopDate()));
            val v = rule.getDateSizeMinutes(LocalDateTime.now(), entity.getStartDate()) / 60.0;
            // 计算如果挖矿时间不大于60的情况
            val levelType = staticService.getItemsLevelType(entity.getMineItemLevel());
            val goldYield = levelType.getGoldYield();
            value.setGoldSize((long) (goldYield * v));
            val integralYield = levelType.getIntegralYield();
            value.setIntegralSize((long) (integralYield * v));
            map.put(entity.getUserId(), value);
        }
        return map;
    }

    @Override
    public void createNewItem(Long userId, Long familyId, long mineId) {
        val mineEntity = mineMapper.selectById(mineId);
        if (Objects.isNull(mineEntity)) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "未知错误，家族可能被解散导致矿区不存在");
        }
        val historyEntity = new DiggingsHistoryEntity();
        historyEntity.setFamilyId(familyId);
        historyEntity.setUserId(userId);
        historyEntity.setSettled(false);
        historyEntity.setIntegral(300);
        val now = LocalDateTime.now();
        historyEntity.setStartDate(now);
        historyEntity.setStopDate(now.plusHours(1));
        historyEntity.setMineItemLevel(mineEntity.getItemLevel());
        historyEntity.setId(idGenerator.getNumberId());
        historyMapper.insert(historyEntity);
        mineEntity.setFamilyId(familyId);
        mineEntity.setUserId(userId);
        mineMapper.updateById(mineEntity);
    }


}
