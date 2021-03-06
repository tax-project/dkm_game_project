package com.dkm.housekeeper.service;

import com.dkm.housekeeper.entity.HousekeeperEntity;
import com.dkm.housekeeper.entity.vo.TbEquipmentVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * @author zhd
 * @date 2020/5/8 16:40
 */
public interface HousekeeperService {

    /**
     * 添加管家状态
     * @param userId
     * @param money
     * @return
     */
    int openHousekeeper(Long userId,BigDecimal money);

    /**
     * 管家剩余天数
     * @param userId
     * @return map
     */
    Map<String,Object> remnantDays(Long userId);

    /**
     * 修改管家时间信息
     * @param userId
     * @return
     */
    Map<String,String> updateTime(Long userId);

    /**
     * 获取宝箱装备
     * @param userId
     * @return
     */
    List<TbEquipmentVo> getBoxEquipment(Long userId);

    /**
     * 判断管家状态，是否保护生产
     */
    boolean housekeeperStatus(Long userId);

    /**
     * 收取种子
     * @param userId
     * @return
     */
    Map<String,Integer> getSeed(Long userId);
}
