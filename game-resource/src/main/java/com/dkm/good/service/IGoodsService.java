package com.dkm.good.service;

import com.dkm.good.entity.vo.GoodsVo;

/**
 * @author qf
 * @date 2020/5/19
 * @vesion 1.0
 **/
public interface IGoodsService {

   /**
    *  添加物品
    * @param vo 物品参数
    */
   void insertGood (GoodsVo vo);
}
