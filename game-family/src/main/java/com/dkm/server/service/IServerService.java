package com.dkm.server.service;

import java.util.Map;

/**
 * @description:
 * @author: zhd
 * @create: 2020-06-19 19:44
 **/
public interface IServerService {

    /**
     * 服务器列表
     * @return
     */
    Map<String,Object> getServerList();

}
