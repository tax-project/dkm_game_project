package com.dkm.goldMine.service.impl;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.family.service.FamilyService;
import com.dkm.goldMine.bean.ao.DataPair;
import com.dkm.goldMine.bean.entity.MineEntity;
import com.dkm.goldMine.bean.entity.MineItemEntity;
import com.dkm.goldMine.bean.vo.FamilyGoldMineVo;
import com.dkm.goldMine.bean.vo.MineItemVo;
import com.dkm.goldMine.dao.MineItemMapper;
import com.dkm.goldMine.dao.MineMapper;
import com.dkm.goldMine.service.IMineService;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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


    @Override
    public FamilyGoldMineVo getFamilyGoldMine(Long familyId) {
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
        FamilyGoldMineVo goldMineVo = new FamilyGoldMineVo();
        goldMineVo.setFamilyId(familyId);
        goldMineVo.setGoldMineId(mineEntity.getId());
        val goldItems = goldMineVo.getGoldItems();
        for (MineItemEntity item : mineItems) {
            MineItemVo itemVo = new MineItemVo();
            itemVo.setGoldItemId(item.getGoldItemId());
            itemVo.setIndex(item.getItemIndex());
            itemVo.setLocationX(item.getLocationX());
            itemVo.setLocationY(item.getLocationY());
            itemVo.setLevel(item.getLevel());
            itemVo.setPrivateItem(item.getLocal() == 0);
            goldItems.add(itemVo);
        }

        return goldMineVo;
    }

    private boolean goldMineNotExists(Long familyId) {
        return mineMapper.selectMineByFamilyId(familyId) == null;
    }

    public void createNewMineByFamilyId(Long familyId) {
        val entity = new MineEntity();
        entity.setFamilyId(familyId);
        val battleId = idGenerator.getNumberId();
        entity.setId(battleId);
        mineMapper.insert(entity);

        val levelList = Arrays.asList(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 9, 10, 11, 12, 12, 13, 14, 14);

        val pairSet = new HashSet<DataPair>();
        for (int i = 0; i < 5; i++) {
            pairSet.add(randomLocation(true));

        }
        while (pairSet.size() != levelList.size()) {
            pairSet.add(randomLocation(false));
        }
        val pairArr = pairSet.toArray(new DataPair[0]);
        for (int i = 0; i < pairArr.length; i++) {
            val mineItemEntity = new MineItemEntity();
            mineItemEntity.setGoldItemId(idGenerator.getNumberId());
            mineItemEntity.setFamilyId(familyId);
            mineItemEntity.setBattleId(battleId);
            mineItemEntity.setLevel(levelList.get(i));
            val dataPair = pairArr[i];
            mineItemEntity.setLocal(checkLocal(dataPair) ? 0 : 1);
            mineItemEntity.setItemIndex(i);
            mineItemEntity.setLocationX(dataPair.getX());
            mineItemEntity.setLocationY(dataPair.getY());
            mineItemMapper.insert(mineItemEntity);
        }
    }

    private Boolean checkLocal(DataPair dataPair) {
        return dataPair.getX() < 6 && dataPair.getY() < 6;
    }

    private DataPair randomLocation(boolean local) {
        int x;
        int y;
        if (local) {
            x = new Random().nextInt(4) + 2;
            y = new Random().nextInt(4) + 2;
        } else {
            x = new Random().nextInt(15) + 2;
            y = new Random().nextInt(16) + 2;
        }
        return new DataPair(x, y);
    }



}
