package com.dkm.wallet.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.wallet.entity.WithdrawalRecord;
import com.dkm.wallet.entity.vo.WithdrawalRecordVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/9 11:33
 */
@Component
public interface WithdrawalRecordMapper extends IBaseMapper<WithdrawalRecord> {

    /**
     * 查询本月的提现记录
     * @param userId
     * @return
     */
    List<WithdrawalRecordVo> queryRecordMonth(Long userId);

    /**
     * 查看本月累计金额
     * @param userId
     * @return
     */
    Double queryRecordMonthStatistics(Long userId);
}
