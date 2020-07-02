package com.dkm.mine2.service.impl;

import com.dkm.mine2.bean.vo.AllMineInfoVo;
import com.dkm.mine2.service.IMine2Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author dragon
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class Mine2ServiceImpl implements IMine2Service {
    @Override
    public AllMineInfoVo getAllInfo(Long userId, Long familyId) {
        return null;
    }
}
