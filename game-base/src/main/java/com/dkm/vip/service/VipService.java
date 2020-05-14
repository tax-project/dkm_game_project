package com.dkm.vip.service;

import java.math.BigDecimal;

/**
 * @author zhd
 * @date 2020/5/8 13:39
 */
public interface VipService {
   /**
    * 开通vip操作
    * @param userId
    * @param money
    * @return
    */
   boolean openVip(Long userId, BigDecimal money);


}
