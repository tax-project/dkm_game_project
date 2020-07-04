package com.dkm.good.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.good.entity.Goods;
import com.dkm.good.entity.vo.GoodQueryVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/19
 * @vesion 1.0
 **/
@Component
public interface GoodMapper extends IBaseMapper<Goods> {

   /**
    *  查询所有物品
    * @param list 用户Id的集合
    * @return 返回所有物品
    */
   List<GoodQueryVo> queryGoodsList(List<Long> list);


   /**
    *  根据用户Id查询所有物品
    * @param userId
    * @return
    */
   List<GoodQueryVo> getGoodList (Long userId);

   /**
    *  随机返回一个物品
    * @return 物品信息
    */
   Goods queryRandomGoods();
}
