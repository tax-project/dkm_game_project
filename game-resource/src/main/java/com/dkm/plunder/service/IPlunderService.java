package com.dkm.plunder.service;

import com.dkm.good.entity.vo.GoodQueryVo;
import com.dkm.plunder.entity.bo.PlunderBO;
import com.dkm.plunder.entity.vo.PlunderVo;

import java.util.List;
import java.util.Map;

/**
 * @author qf
 * @date 2020/5/19
 * @vesion 1.0
 **/
public interface IPlunderService {

   /**
    *  增加掠夺表
    *  掠夺人
    * @param vo 掠夺信息
    */
   void insertPlunder (PlunderVo vo);


   /**
    *  展示掠夺物品的列表
    * @return 返回列表
    */
   Map<String,Object> queryPlunderList ();

   /**
    *  根据被抢人的用户Id查询所有物品
    * @param userId
    * @return
    */
   PlunderBO getGoodByUserId (Long userId);
}
