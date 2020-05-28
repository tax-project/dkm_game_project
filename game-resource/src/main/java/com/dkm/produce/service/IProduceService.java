package com.dkm.produce.service;

import com.dkm.produce.entity.vo.AttendantGoods;

import java.util.List;
import java.util.Map;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/19 21:25
 */
public interface IProduceService {

    /**
     *  添加产出
     * @param attendantId
     */
    Map<String,Object> insertProduce (Long attendantId);
    /**
     * 根据用户id查询跟班和跟班产生的物品
     */
    List<AttendantGoods> queryJoinOutPutGoods(Long userId);


}
