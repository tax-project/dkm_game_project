package com.dkm.mine2.service;


import com.dkm.mine2.bean.vo.MineInfoVo;

/**
 * @author OpenE
 */
public interface IMine2Service {
    MineInfoVo getAllInfo(Long userId, Long familyId);
}
