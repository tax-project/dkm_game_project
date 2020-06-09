package com.dkm.pay.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.pay.entity.Pay;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qf
 * @date 2020/6/9
 * @vesion 1.0
 **/
@Component
public interface PayMapper extends IBaseMapper<Pay> {

   /**
    *   根据订单号集合查询所有支付信息
    * @param list 订单号集合
    * @return 支付记录
    */
   List<Pay> queryPayList (List<String> list);
}
