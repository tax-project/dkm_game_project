package com.dkm.mine2.rule;

import com.dkm.mine2.bean.entity.MineBattleEntity;
import com.dkm.mine2.bean.entity.MineBattleItemEntity;
import com.dkm.mine2.dao.MineBattleItemMapper;
import com.dkm.mine2.dao.MineBattleMapper;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.BufferedReader;
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
public class BattleItemRule {
    @Resource
    private IdGenerator idGenerator;
    @Resource
    private MineBattleItemMapper mineBattleItemMapper;
    @Resource
    private MineBattleMapper mineBattleMapper;

    /**
     * 矿厂生成规则
     */
    public MineBattleEntity createBattle() {
        val mineBattleEntity = new MineBattleEntity();
        val mineBattleEntityId = idGenerator.getNumberId();
        mineBattleEntity.setId(mineBattleEntityId);
        mineBattleMapper.insert(mineBattleEntity);
        //开始生成私有的

        for (int i = 1; i < 5; i++) {
            val privateList = Arrays.asList(1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4);
            for (Integer level : privateList) {
                val mineBattleItemEntity = new MineBattleItemEntity();
                mineBattleItemEntity.setId(idGenerator.getNumberId());
                mineBattleItemEntity.setLevel(level);
                mineBattleItemEntity.setBattleId(mineBattleEntityId);
                mineBattleItemEntity.setBelongItem(i);
                mineBattleItemMapper.insert(mineBattleItemEntity);
            }
        }
        //开始生成公开的
        for (int i = 0; i < 41; i++) {
            val mineBattleItemEntity = new MineBattleItemEntity();
            mineBattleItemEntity.setId(idGenerator.getNumberId());
            mineBattleItemEntity.setLevel(i%7 + 5);
            mineBattleItemEntity.setBattleId(mineBattleEntityId);
            mineBattleItemEntity.setBelongItem(0);
            mineBattleItemMapper.insert(mineBattleItemEntity);
        }
        return mineBattleEntity;
    }
}
