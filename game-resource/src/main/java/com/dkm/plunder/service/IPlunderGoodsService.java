package com.dkm.plunder.service;

import com.dkm.plunder.entity.vo.PlunderGoodsVo;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/19
 * @vesion 1.0
 **/
public interface IPlunderGoodsService {

   /**
    *  批量增加产出商品信息
    * @param list 产品商品信息
    */
   void insertAllPlunderGoods (List<PlunderGoodsVo> list);

   /**
    *  增加产出商品信息
    * @param vo 产品商品信息
    */
   void insertPlunderGoods (PlunderGoodsVo vo);
}
