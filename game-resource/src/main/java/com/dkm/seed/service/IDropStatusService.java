package com.dkm.seed.service;

import com.dkm.seed.entity.DropStatus;

/**
 * @author qf
 * @date 2020/7/31
 * @vesion 1.0
 **/
public interface IDropStatusService {

   /**
    *  增加或者修改
    * @param dropStatus 参数
    */
   void dropStatusUpdate(DropStatus dropStatus);

   /**
    *  查询一条记录
    * @param userId 用户id
    * @return 返回结果
    */
   DropStatus queryDropStatus (Long userId);

   /**
    *  删除
    * @param userId 用户Id
    */
   void deleteDrop (Long userId);
}
