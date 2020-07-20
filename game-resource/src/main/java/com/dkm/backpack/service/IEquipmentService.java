package com.dkm.backpack.service;

import com.dkm.backpack.entity.vo.EquipmentVo;
import com.dkm.backpack.entity.vo.UserEquipmentVo;

import java.util.List;

/**
 * @description:
 * @author: zhd
 * @create: 2020-07-18 09:18
 **/
public interface IEquipmentService {

    /**
     * 获取装备信息
     * @param backpackId
     * @return
     */
    EquipmentVo getEquipmentInfo(Long backpackId);

    /**
     * 获取用户当前装备中的数据
     * @param userId
     * @return
     */
    List<UserEquipmentVo> getUserEquipment(Long userId);

    /**
     * 装备或卸下
     * @param userId
     * @param backpackId
     */
    void removeOrEquipment(Long userId,Long backpackId);
}
