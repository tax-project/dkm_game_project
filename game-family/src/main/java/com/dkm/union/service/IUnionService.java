package com.dkm.union.service;

import java.util.Map;

/**
 * @description: 工会
 * @author: zhd
 * @create: 2020-06-18 10:41
 **/
public interface IUnionService {

    /**
     * 工会信息
     * @param unionId
     * @return
     */
    Map<String,Object> getUnionInfo(Long unionId);

}
