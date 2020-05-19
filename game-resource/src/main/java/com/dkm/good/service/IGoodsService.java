package com.dkm.good.service;

import com.dkm.good.entity.Goods;
import com.dkm.good.entity.vo.GoodQueryVo;
import com.dkm.good.entity.vo.GoodsVo;

import java.util.List;

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

   /**
    *  查询所有物品
    * @param list
    * @return
    */
   List<GoodQueryVo> queryGoodsList (List<Long> list);

   /**
    *  随机生成一个物品
    * @return 返回物品信息
    */
   Goods queryRandomGoods ();
}
