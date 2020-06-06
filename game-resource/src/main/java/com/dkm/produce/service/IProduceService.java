package com.dkm.produce.service;

import com.dkm.produce.entity.vo.AttendantGoods;
import com.dkm.produce.entity.vo.AttendantPutVo;

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
     * @param attendantId 跟班id
     * @param attUserId 跟班用户id
     * @return 返回数据
     */
    Map<String,Object> insertProduce (Long attendantId, Long attUserId);
    /**
     * 根据用户id查询跟班和跟班产生的物品
     */
    List<AttendantGoods> queryJoinOutPutGoods(Long userId);

    /**
     *  查询所有跟班产出的物品
     * @param userId 用户id
     * @return 返回当前用户产出的物品
     */
    List<AttendantPutVo> queryOutput(Long userId);

    /**
     * 修该掠夺赢了之后
     * 修改产出输方产出的物品
     * @param id  产出id
     * @return
     */
   int deleteOutGoodNumber(Long id);
}
