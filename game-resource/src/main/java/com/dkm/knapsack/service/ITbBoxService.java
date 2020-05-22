package com.dkm.knapsack.service;


import com.dkm.knapsack.domain.TbBox;
import com.dkm.knapsack.domain.vo.TbEquipmentVo;

import java.util.List;

/**
 * <p>
 * 宝箱表 服务类
 * </p>
 *
 * @author zy
 * @since 2020-05-14
 */
public interface ITbBoxService{
    void addTbBox(TbBox tbBox);

    TbEquipmentVo selectByBoxId(String boxId);

    List<TbBox> selectAll();

    List<TbEquipmentVo> selectByBoxIdTwo(List<Long> boxId);
}
