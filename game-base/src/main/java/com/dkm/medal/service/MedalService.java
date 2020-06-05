package com.dkm.medal.service;

import com.dkm.medal.entity.vo.MedalUserInfoVo;

import java.util.List;

/**
 * @program: game_project
 * @description: 勋章
 * @author: zhd
 * @create: 2020-06-05 14:55
 **/
public interface MedalService {


    /**
     * 获取用户勋章信息
     * @param userId
     * @param type
     * @return
     */
    List<MedalUserInfoVo> getUserMedal(Long userId,Integer type);
    /**
     * 获取用户勋章详情
     * @param userId
     * @param medalId
     * @return
     */
    MedalUserInfoVo getOneUserMedal(Long userId,Long medalId);
}
