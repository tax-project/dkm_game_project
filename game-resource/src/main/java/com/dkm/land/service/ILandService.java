package com.dkm.land.service;

import com.dkm.land.entity.Land;
import com.dkm.land.entity.vo.UserLandUnlock;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:土地
 * @USER: 刘梦祺
 * @DATE: 2020/5/9 9:37
 */
public interface ILandService {
    /**
     * 土地批量增加
     * @param list
     * @return null
     */
    void insertLand(List<Land> list);

    /**
     * 根据用户id查询土地
     * @return List<Land>
     */
    List<UserLandUnlock> queryUserByIdLand();
}
