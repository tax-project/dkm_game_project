package com.dkm.mine2.service;


import com.dkm.mine2.bean.vo.AllMineInfoVo;

/**
 * @author OpenE
 */
public interface IMine2Service {
    AllMineInfoVo getAllInfo(Long userId, Long familyId);
}
