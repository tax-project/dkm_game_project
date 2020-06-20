package com.dkm.bill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.bill.dao.BillMapper;
import com.dkm.bill.entity.Bill;
import com.dkm.bill.entity.vo.BillVo;
import com.dkm.bill.service.IBillService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.utils.DateUtils;
import com.dkm.wallet.entity.Withdrawal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/20 14:42
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements IBillService {


    @Autowired
    private LocalUser localUser;

    @Override
    public List<Bill> queryAllBill(BillVo vo) {

        //根据状态id查询
        List<Bill> bills = baseMapper.queryAllBill(vo.getBType(), vo.getBIncomeExpenditure(), localUser.getUser().getId());

        bills.forEach(a->a.setTime(DateUtils.formatDateTime(a.getBTime())));

        return bills;

    }
}
