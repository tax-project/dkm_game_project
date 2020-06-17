package com.dkm.wallet.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.wallet.entity.Withdrawal;
import com.dkm.wallet.entity.WithdrawalRecord;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/9 13:54
 */
@Component
public interface WithdrawalMapper extends IBaseMapper<Withdrawal> {

    void insertWithdrawalData(List<Withdrawal> list);

}
