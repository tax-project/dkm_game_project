package com.dkm.goldMine.service;

import com.dkm.goldMine.bean.vo.FamilyGoldMineVo;

public interface IMineService {
    FamilyGoldMineVo getFamilyGoldMine(Long familyIdInt);
    void createNewMineByFamilyId(Long familyId);
}
