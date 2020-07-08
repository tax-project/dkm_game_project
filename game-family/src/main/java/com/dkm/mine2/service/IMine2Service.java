package com.dkm.mine2.service;


import com.dkm.mine2.bean.vo.BattleItemPropVo;
import com.dkm.mine2.bean.vo.FamilyAdditionVo2Entity;
import com.dkm.mine2.bean.vo.MineVo;

import java.util.List;

/**
 * @author OpenE
 */
public interface IMine2Service {
    MineVo getAllInfo(Long userId, Long familyId);

    List<BattleItemPropVo> getItemsLevelType();

    List<FamilyAdditionVo2Entity> getFamilyType();
}
