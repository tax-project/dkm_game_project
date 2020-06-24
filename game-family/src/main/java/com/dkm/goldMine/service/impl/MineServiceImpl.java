package com.dkm.goldMine.service.impl;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.family.dao.FamilyDao;
import com.dkm.family.service.FamilyService;
import com.dkm.feign.UserFeignClient;
import com.dkm.goldMine.bean.ao.DataPair;
import com.dkm.goldMine.bean.ao.UserInfoBO;
import com.dkm.goldMine.bean.entity.MineEntity;
import com.dkm.goldMine.bean.entity.MineItemEntity;
import com.dkm.goldMine.bean.enums.GoldItemLevel;
import com.dkm.goldMine.bean.vo.*;
import com.dkm.goldMine.dao.MineItemMapper;
import com.dkm.goldMine.dao.MineMapper;
import com.dkm.goldMine.service.IMineService;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class MineServiceImpl implements IMineService {

    @Resource
    private IdGenerator idGenerator;
    @Resource
    private MineMapper mineMapper;
    @Resource
    private MineItemMapper mineItemMapper;
    @Resource
    private FamilyService familyService;
    @Resource
    private FamilyDao familyDao;

    @Autowired
    UserFeignClient userFeignClient;

    @Override
    public GoldMineVo getFamilyGoldMine(Long familyId) {
        if (!familyService.familyExists(familyId)) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "家族 id [" + familyId + "] 不存在！");
        }
        if (goldMineNotExists(familyId)) {
            createNewMineByFamilyId(familyId);
        }
        val mineEntity = mineMapper.selectMineByFamilyId(familyId);
        val mineItems = mineItemMapper.selectByBattleId(mineEntity.getId());
        if (mineItems == null || mineItems.size() == 0) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "此时不应该无数据，在创建来自[" + familyId + "]数据表时发生问题。");
        }
        GoldMineVo goldMineVo = new GoldMineVo();
        goldMineVo.setFamilyId(familyId);
        val familyGrade = familyDao.selectById(familyId).getFamilyGrade();
        //等级
        goldMineVo.setFamilyLevel(familyGrade);
        goldMineVo.setGoldMineId(mineEntity.getId());
        val goldItems = goldMineVo.getGoldItems();
        for (MineItemEntity item : mineItems) {
            MineItemVo itemVo = new MineItemVo();
            itemVo.setGoldItemId(item.getGoldItemId());
            itemVo.setOccupied(item.getUserId() != 0);
            itemVo.setIndex(item.getItemIndex());
            itemVo.setLevel(item.getLevel());
            itemVo.setPrivateItem(item.getLocal() == 0);
            goldItems.add(itemVo);
        }

        return goldMineVo;
    }


    @Override
    public void createNewMineByFamilyId(Long familyId) {
        val entity = new MineEntity();
        entity.setFamilyId(familyId);
        val battleId = idGenerator.getNumberId();
        entity.setId(battleId);
        mineMapper.insert(entity);
        val privateList = Arrays.asList(1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4);
        val items = new ArrayList<Pair<Boolean, Integer>>();
        for (Integer level : privateList) {
            items.add(Pair.of(false, level));
        }
        for (int i = 0; i < 41; i++) {
            items.add(Pair.of(true, randomLevel(3, 10)));
        }
        for (int i = 0; i < items.size(); i++) {
            val integerPair = items.get(i);
            val mineItemEntity = new MineItemEntity();
            mineItemEntity.setGoldItemId(idGenerator.getNumberId());
            mineItemEntity.setFamilyId(familyId);
            mineItemEntity.setBattleId(battleId);
            mineItemEntity.setLevel(integerPair.getSecond());
            mineItemEntity.setLocal(integerPair.getFirst() ? 0 : 1);
            mineItemEntity.setItemIndex(i);
            mineItemMapper.insert(mineItemEntity);
        }

    }

    private int randomLevel(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    @Override
    public MineItemFightVo getGoldMineItemInfo(Long familyId, Long goldItemId) {
        MineItemFightVo npcVo = new MineItemFightVo();
        val itemEntity = mineItemMapper.selectByBattleIdAndGoldItemId(familyId, goldItemId);
        if (itemEntity == null) {
            log.error("id 未发现！" + familyId + " | " + goldItemId);
            throw new ApplicationException(CodeType.SERVICE_ERROR, "未发现id的表");
        }
        val goldItemLevel = GoldItemLevel.valueOf("LEVEL_" + itemEntity.getLevel());
        npcVo.getProfitPerHour().setGold(goldItemLevel.getGoldYield());
        npcVo.getProfitPerHour().setIntegral(goldItemLevel.getIntegralYield());
        npcVo.setUserId(itemEntity.getUserId());
        val familyGrade = familyDao.selectById(familyId).getFamilyGrade();
        //等级
        npcVo.setOurLevel(familyGrade);
        npcVo.setUserFamilyId(itemEntity.getFamilyId());
        if (itemEntity.getUserId() == 0) {
            // 这是空的
            npcVo.setNpc(true);
            npcVo.setFightName(goldItemLevel.getNpcName());
            npcVo.setNpcLevel(goldItemLevel.getNpcLevel());
            npcVo.setOccupiedStartDate("");
            npcVo.setOccupiedEndDate("");
            npcVo.setSuccessRate((((npcVo.getOurLevel() * 100.0) / (npcVo.getNpcLevel() * 100.0 + npcVo.getOurLevel() * 100.0)) * 100.0) / 1 + "%");

        } else {
            npcVo.setNpc(false);
            npcVo.setFightName("");
            npcVo.setNpcLevel(0);
            npcVo.setOccupiedStartDate(itemEntity.getFightStartDate().toString());
            npcVo.setOccupiedEndDate(itemEntity.getFightEndDate().toString());
            npcVo.setSuccessRate("50%");
        }
        updateInfo(itemEntity, npcVo);
        return npcVo;
    }

    /**
     * 更新矿区数据
     *
     * @param
     * @param
     * @param npcVo
     */

    private void updateInfo(MineItemEntity itemEntity, MineItemFightVo npcVo) {
        if (!npcVo.isNpc()) {
            if (itemEntity.getFightEndDate().isAfter(LocalDateTime.now())) {
                try {
                    disOccupied(itemEntity);
                } catch (Exception ignored) {
                }
            }
        }

    }

    @Override
    public FightVo fight(Long userId, Long familyId, Long goldItemId) {
        FightVo fightVo = new FightVo();
        val itemEntity = mineItemMapper.selectByBattleIdAndGoldItemId(familyId, goldItemId);

        val familyEntity = familyDao.selectById(familyId);
        if (familyEntity == null) {
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "用户不属于此家族");
        }
        val familyGrade = familyEntity.getFamilyGrade(); //等级
        if (itemEntity.getLevel() > familyGrade && (itemEntity.getLevel() - familyGrade) > 3) {
            fightVo.setFightStatus(false);
        } else {
            fightVo.setFightStatus(true);
            itemEntity.setUserId(userId);
            itemEntity.setFightStartDate(LocalDateTime.now());
            itemEntity.setFightEndDate(LocalDateTime.now().plusHours(1));
            mineItemMapper.updateById(itemEntity);
        }
        return fightVo;
    }

    @Override
    public FightKillVo fightKill(Long familyId, Long goldItemId, Long userId) {
        val fightKillVo = new FightKillVo();
        val itemEntity = mineItemMapper.selectByBattleIdAndGoldItemId(familyId, goldItemId);
        if (!itemEntity.getUserId().equals(userId)) {
            fightKillVo.setKill(false);
            fightKillVo.setKillMessage("呐，你不能阻止其他人挖矿啊");
        } else {
            Pair<Long, Long> longLongPair;
            try {
                longLongPair = disOccupied(itemEntity);
            } catch (ParseException e) {
                throw new ApplicationException(CodeType.SERVICE_ERROR, e.getMessage());
            }
            fightKillVo.setAddIntegral(Math.toIntExact(longLongPair.getSecond()));
            fightKillVo.setAddMoney(Math.toIntExact(longLongPair.getFirst()));
        }

        return fightKillVo;
    }

    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Pair<Long, Long> disOccupied(MineItemEntity itemEntity) throws ParseException {
        //数据结束，销毁！
        val moneyAndIntegral = getMoneyAndIntegral(itemEntity);
        val userInfoBO = new UserInfoBO();
        userInfoBO.setUserId(itemEntity.getUserId());
        userInfoBO.setUserInfoGold(Math.toIntExact(moneyAndIntegral.getFirst()));
//        userInfoBO.setu(Math.toIntExact(moneyAndIntegral.getFirst()));
        userFeignClient.updateGold(userInfoBO);
        itemEntity.setFightEndDate(null);
        itemEntity.setFightStartDate(null);
        itemEntity.setUserId(0L);
        mineItemMapper.updateById(itemEntity);
        return moneyAndIntegral;
    }

    /**
     * 得到当前赚取
     *
     * @param itemEntity
     * @return
     * @throws ParseException
     */
    public Pair<Long, Long> getMoneyAndIntegral(MineItemEntity itemEntity) throws ParseException {
        if (itemEntity.getFightStartDate() == null || itemEntity.getFightEndDate() == null) {
            return Pair.of(0L, 0L);
        }
        val endTime = dateFormat.parse(itemEntity.getFightEndDate().format(timeFormatter)).getTime();
        val startTime = dateFormat.parse(itemEntity.getFightStartDate().format(timeFormatter)).getTime();
        double dateHour = (endTime - startTime) / 1000.0 / 3600.0;
        val goldYield = GoldItemLevel.valueOf("LEVEL_" + itemEntity.getLevel()).getGoldYield();
        val integralYield = GoldItemLevel.valueOf("LEVEL_" + itemEntity.getLevel()).getIntegralYield();
        return Pair.of((long) (dateHour * goldYield), (long) (integralYield * dateHour));

    }


    private boolean goldMineNotExists(Long familyId) {
        return mineMapper.selectMineByFamilyId(familyId) == null;
    }


}
