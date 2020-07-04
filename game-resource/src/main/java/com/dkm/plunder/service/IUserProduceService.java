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
    *  增加产出信息
    * @param userProduce
    */
   void insertProduce (UserProduce userProduce);

   /**
    *  批量增加用户产出信息
    * @param list 参数集合
    */
   void allInsertUserProduce (List<UserProduce> list);
}
