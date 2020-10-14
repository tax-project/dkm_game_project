package com.dkm.bill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.bill.dao.BillMapper;
import com.dkm.bill.entity.Bill;
import com.dkm.bill.entity.vo.BillVo;
import com.dkm.bill.service.IBillService;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.feign.UserFeignClient;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.utils.DateUtils;
import com.dkm.wallet.entity.Withdrawal;
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
 * @DATE: 2020/6/20 14:42
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements IBillService {


    @Autowired
    private LocalUser localUser;

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public Map<String,Object> queryAllBill(BillVo vo) {
        Map<String,Object> map=new HashMap<>();
        //查询用户服务的用户信息
        Result<UserInfoQueryBo> userInfoQueryBoResult = userFeignClient.queryUser(localUser.getUser().getId());
        if(vo.getBType()==1){
            //金币
            map.put("gold",userInfoQueryBoResult.getData().getUserInfoGold());
        }

        if(vo.getBType()==2){
            //钻石
            map.put("Diamonds",userInfoQueryBoResult.getData().getUserInfoDiamonds());
        }

        if(vo.getBType()==3){
            //财富卷
            map.put("FortuneVolume",1);
        }

        //根据状态id查询
        List<Bill> bills = baseMapper.queryAllBill(vo.getBType(), vo.getBIncomeExpenditure(), localUser.getUser().getId());
        //对集合循环
        bills.forEach(a->a.setTime(DateUtils.formatDateTime(a.getBTime())));

        map.put("bills",bills);

        return map;

    }
}
