package com.dkm.diggings.rule;

import com.dkm.diggings.bean.entity.DiggingsEntity;
import com.dkm.diggings.bean.entity.MineEntity;
import com.dkm.diggings.dao.DiggingsMapper;
import com.dkm.diggings.dao.MineMapper;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * 矿场的生成规则
 *
 * @author OpenE
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class MineRule {
    @Resource
    private IdGenerator idGenerator;
    @Resource
    private MineMapper mineMapper;
    @Resource
    private DiggingsMapper diggingsMapper;

    /**
     * 矿区生成规则
     */
    public DiggingsEntity createNewBattle() {
        val mineBattleEntity = new DiggingsEntity();
        val mineBattleEntityId = idGenerator.getNumberId();
        mineBattleEntity.setId(mineBattleEntityId);
        diggingsMapper.insert(mineBattleEntity);
        //开始生成私有的
        val list = new ArrayList<MineEntity>(30);
        for (int i = 1; i < 5; i++) {
            val privateList = Arrays.asList(1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4);
            for (Integer level : privateList) {
                val mineBattleItemEntity = new MineEntity();
                mineBattleItemEntity.setId(idGenerator.getNumberId());
                mineBattleItemEntity.setItemLevel(level);
                mineBattleItemEntity.setBattleId(mineBattleEntityId);
                mineBattleItemEntity.setBelongItem(i);
                list.add(mineBattleItemEntity);
            }
        }
        //开始生成公开的
        for (int i = 0; i < 41; i++) {
            val mineBattleItemEntity = new MineEntity();
            mineBattleItemEntity.setId(idGenerator.getNumberId());
            mineBattleItemEntity.setItemLevel(i % 7 + 5);
            mineBattleItemEntity.setBattleId(mineBattleEntityId);
            mineBattleItemEntity.setBelongItem(0);
            list.add(mineBattleItemEntity);
        }
        mineMapper.insertAll(list);
        return mineBattleEntity;
    }

    /**
     * 占领的成功率
     */
    public Double calculateSuccessRate(int ourSkillLevel, int herSkillLevel) {
        if (ourSkillLevel - herSkillLevel >= 8) {
            return 1.0;
        }
        if (herSkillLevel - ourSkillLevel >= 8) {
            return 0.0;
        }
        double abs = Math.abs(ourSkillLevel - herSkillLevel) / 8.0;
        if (herSkillLevel > ourSkillLevel) {
            return 1 - abs;
        } else if (herSkillLevel == ourSkillLevel) {
            return 0.5;
        } else {
            return abs;
        }
    }

    public long getDateSizeMinutes(LocalDateTime now, LocalDateTime startDate) {
        val between = Duration.between(startDate, now);
        final long l = between.toMillis();
        return l > 60 ? 60 : l;
    }

    public Integer chooseGoldOrIntegralYield(long minutes, double maxSize) {
        val v = minutes / 60.0;
        return (int) (maxSize * v);
    }


    public boolean occupy(Double successRate) {
        if (successRate > 0.8) {
            return true;
        }
        int v = (int) (successRate * 100.0);
        val i = new Random().nextInt(100);
        return v > i;
    }

    public int getUserOccupationSize(Long userId) {
        return 3;
    }
}
