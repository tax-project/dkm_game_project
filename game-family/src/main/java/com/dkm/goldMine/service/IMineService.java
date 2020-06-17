package com.dkm.goldMine.service;

import com.dkm.goldMine.bean.vo.*;

public interface IMineService {
    FamilyGoldMineVo getFamilyGoldMine(Long familyIdInt);
    void createNewMineByFamilyId(Long familyId);
    MineItemNpcVo getGoldMineItemInfo(Long familyId, Long goldItemId);

    FightVo fightMineItem(Long familyId, Long goldItemId);
    TryFightVo tryFightMineItem(Long familyId, Long goldItemId);
}
