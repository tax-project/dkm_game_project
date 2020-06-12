package com.dkm.wallet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.wallet.dao.WithdrawalRecordMapper;
import com.dkm.wallet.entity.WithdrawalRecord;
import com.dkm.wallet.service.IWithdrawalRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/9 11:32
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class WithdrawalRecordServiceImpl extends ServiceImpl<WithdrawalRecordMapper, WithdrawalRecord> implements IWithdrawalRecordService {

    @Autowired
    private LocalUser localUser;

    /**
     * 查看本月提现记录
     * @return
     */
    @Override
    public Map<String,Object> queryRecordMonth() {
        Map<String,Object> map=new HashMap<>(16);

        //本月提现记录
        List<WithdrawalRecord> withdrawalRecords = baseMapper.queryRecordMonth(localUser.getUser().getId());

        //本月累计金额
        Double aDouble = baseMapper.queryRecordMonthStatistics(localUser.getUser().getId());

        map.put("withdrawalRecords",withdrawalRecords);

        map.put("aDouble",aDouble);

        return map;
    }





}
