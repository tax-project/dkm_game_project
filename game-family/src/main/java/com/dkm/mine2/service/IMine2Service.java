package com.dkm.mine2.service;


import com.dkm.mine2.bean.vo.BattleItemPropVo;
import com.dkm.mine2.bean.vo.MineInfoVo;

import java.util.List;

/**
 * @author OpenE
 */
public interface IMine2Service {
    MineInfoVo getAllInfo(Long userId, Long familyId);

    List<BattleItemPropVo> getItemsLevelInfo();
}
