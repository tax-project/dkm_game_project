package com.dkm.diggings.service.impl;

import com.dkm.constanct.CodeType;
import com.dkm.diggings.bean.entity.DiggingsHistoryEntity;
import com.dkm.diggings.dao.DiggingsHistoryMapper;
import com.dkm.diggings.dao.MineMapper;
import com.dkm.diggings.service.IHistoryService;
import com.dkm.diggings.service.IOccupiedService;
import com.dkm.diggings.service.IStaticService;
import com.dkm.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
            throw new ApplicationException(CodeType.SERVICE_ERROR, "未知错误");
        }
        if (entity.isSettled()) {
            return;
        }
        entity.setSettled(true);
        entity.setStopDate(now);
        historyMapper.updateById(entity);
        flushData(entity.getMineItemLevel(), entity.getUserId(), mineId);
    }

    private void flushData(int mineItemLevel, long userId, long mineId) {
        val mineEntity = mineMapper.selectById(mineId);
        if (Objects.isNull(mineEntity)) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "未知错误，家族可能被解散导致矿区不存在");
        }
        if (mineEntity.getUserId() == userId) {
            mineEntity.setUserId(0);
        }
        val levelType = staticService.getItemsLevelType(mineItemLevel);


    }


}
