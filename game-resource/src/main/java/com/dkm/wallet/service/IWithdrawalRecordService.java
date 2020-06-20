package com.dkm.wallet.service;


import java.util.Map;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/9 11:31
 */
public interface IWithdrawalRecordService {

    /**
     * 查询本月的提现记录
     * @return
     */
    Map<String,Object> queryRecordMonth();




}
