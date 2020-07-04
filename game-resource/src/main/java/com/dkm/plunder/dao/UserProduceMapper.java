package com.dkm.plunder.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.plunder.entity.UserProduce;
import com.dkm.plunder.entity.vo.UserProduceVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户产出mapper
 * @author qf
 * @date 2020/5/19
 * @vesion 1.0
 **/
@Component
public interface UserProduceMapper extends IBaseMapper<UserProduce> {

   /**
    *  批量增加用户产出信息
    * @param list
    * @return
    */
   Integer allInsertUserProduce(List<UserProduce> list);
}
