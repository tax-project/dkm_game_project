package com.dkm.medal.service;

import com.dkm.medal.entity.Medal;


import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @USER: 刘梦祺
 * @DATE: 2020/5/10 15:37
 */
public interface IMedalService {
    /**
     * 根据用id查询勋章(详情)
     * @param userId
     * @return  List<Medal>
     */
    List<Medal> queryMedalById();

    /**
     * 根据用id查询勋章详情
     * @param medalId
     * @return  List<Medal>
     */
    List<Medal> queryMedalByIdDetails(Integer medalId);
}
