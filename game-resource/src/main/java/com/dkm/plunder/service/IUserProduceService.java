package com.dkm.plunder.service;

import com.dkm.plunder.entity.UserProduce;
import com.dkm.plunder.entity.vo.UserProduceVo;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/19
 * @vesion 1.0
 **/
public interface IUserProduceService {

   /**
    *  批量增加一个用户所有的产出信息
    * @param list 用户产出信息
    */
   void insertAllProduceInfo (List<UserProduceVo> list);

   /**
    *  增加产出信息
    * @param userProduce
    */
   void insertProduce (UserProduce userProduce);
}
