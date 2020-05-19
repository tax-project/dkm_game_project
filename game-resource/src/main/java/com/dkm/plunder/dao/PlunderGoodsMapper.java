package com.dkm.plunder.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.plunder.entity.PlunderGoods;
import com.dkm.plunder.entity.vo.PlunderGoodsVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/19
 * @vesion 1.0
 **/
@Component
public interface PlunderGoodsMapper extends IBaseMapper<PlunderGoods> {

   /**
    *  批量增加产出物品信息
    * @param list 产出物品信息
    * @return 增加结果
    */
   Integer insertAllPlunderGoods(List<PlunderGoodsVo> list);
}
