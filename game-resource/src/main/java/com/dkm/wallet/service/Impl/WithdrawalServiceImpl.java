package com.dkm.wallet.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.feign.UserFeignClient;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.seed.entity.LandSeed;
import com.dkm.wallet.dao.WithdrawalMapper;
import com.dkm.wallet.entity.Withdrawal;
import com.dkm.wallet.service.IWithdrawalService;
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
 * @DATE: 2020/6/9 13:56
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class WithdrawalServiceImpl extends ServiceImpl<WithdrawalMapper, Withdrawal> implements IWithdrawalService {

    @Autowired
    private LocalUser localUser;

    @Autowired
    private UserFeignClient userFeignClient;


    /**
     * 查询所有提现数据
     * @return
     */
    @Override
    public Map<String,Object> queryAllWithdrawalData() {
        Map<String,Object> map=new HashMap<>();

        UserLoginQuery user = localUser.getUser();

        //查询已经种植的种子
        LambdaQueryWrapper<Withdrawal> queryWrapper = new LambdaQueryWrapper<Withdrawal>()
                .eq(Withdrawal::getUserId, user.getId());

        List<Withdrawal> withdrawals = baseMapper.selectList(queryWrapper);

        Result<UserInfoQueryBo> userInfoQueryBoResult = userFeignClient.queryUser(user.getId());

        map.put("withdrawals",withdrawals);
        //余额
        map.put("balance",userInfoQueryBoResult.getData().getUserInfoPacketBalance());

        return map;
    }



}
