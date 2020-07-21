package com.dkm.box.service;

import com.dkm.backpack.entity.vo.OpenEquipmentVo;
import com.dkm.backpack.entity.vo.UserEquipmentVo;
import com.dkm.box.entity.vo.BoxInfoVo;

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

    /**
     * 开启宝箱
     * @param boxId
     * @param userId
     */
    List<OpenEquipmentVo> openBox(Long userId, Long boxId);

}
