package com.dkm.wallet.service;

import com.dkm.land.entity.vo.Message;
import com.dkm.wallet.entity.Withdrawal;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/9 13:55
 */
public interface IWithdrawalService {

    /**
     * 查询所有提现数据
     */
    Map<String,Object> queryAllWithdrawalData();

    /**
     * 提现
     * @param id
     * @return
     */
    Message withdrawal(Long id);




}
