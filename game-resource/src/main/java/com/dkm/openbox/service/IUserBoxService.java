package com.dkm.openbox.service;

import com.dkm.openbox.entity.vo.BoxInfoVo;

import java.util.List;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-07-17 14:29
 **/
public interface IUserBoxService {

    /**
     * 获取用户宝箱信息
     * @param userId
     * @return
     */
    List<BoxInfoVo> getBoxInfo(Long userId);
}
