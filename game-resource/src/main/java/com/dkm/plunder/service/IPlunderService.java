package com.dkm.plunder.service;

import com.dkm.plunder.entity.vo.PlunderVo;

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
}
