package com.dkm.integral.service;

import com.dkm.integral.entity.Integral;
import com.dkm.integral.entity.Stars;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/22 18:42
 */
public interface IIntegralService {
    /**
     * 查询所有积分产品
     * @return
     */
    List<Integral> queryAllIntegral();

    /**
     * 根据用户id查询用户积分
     * @return
     */
    Integer queryUserIdIntegral();

    /**
     * 根据用户id修改用户积分
     * @param iMyIntegral
     * @return
     */
    int updateUserIntegral(Integer iMyIntegral);

    /**
     * 根据用户id修改用户所拥有的消耗数量
     * @return
     */
    int updateUserStarsNumber(Integer sCurrentlyHasNum,Integer sStar);
}
