package com.dkm.backpack.service;

import com.dkm.backpack.entity.vo.EquipmentVo;

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
}
