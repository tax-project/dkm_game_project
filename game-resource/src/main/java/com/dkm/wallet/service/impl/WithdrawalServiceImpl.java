package com.dkm.wallet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.feign.UserFeignClient;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.land.entity.vo.Message;
import com.dkm.seed.entity.LandSeed;
import com.dkm.utils.IdGenerator;
import com.dkm.wallet.dao.WithdrawalMapper;
import com.dkm.wallet.entity.Withdrawal;
import com.dkm.wallet.service.IWithdrawalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Autowired
    private IdGenerator idGenerator;


    /**
     * 查询所有提现数据
     * @return
     */
    @Override
    public Map<String,Object> queryAllWithdrawalData() {
        Map<String,Object> map=new HashMap<>();

        UserLoginQuery user = localUser.getUser();

        Result<UserInfoQueryBo> userInfoQueryBoResult = userFeignClient.queryUser(user.getId());

        Double money=0.1;

        List<Withdrawal> list=new ArrayList<>();

        LambdaQueryWrapper<Withdrawal> queryWrapper = new LambdaQueryWrapper<Withdrawal>()
                .eq(Withdrawal::getUserId, user.getId());

        List<Withdrawal> withdrawals = baseMapper.selectList(queryWrapper);
        //如果等于0 初始化数据
        if(withdrawals.size()==0){
            for (int i = 1; i <=39; i++) {
                Withdrawal withdrawal=new Withdrawal();
                withdrawal.setId(idGenerator.getNumberId());
                withdrawal.setUserId(user.getId());

                if(userInfoQueryBoResult.getData().getUserInfoGrade()>i){
                    withdrawal.setWithdrawalStatus(1);
                }

                if(userInfoQueryBoResult.getData().getUserInfoGrade()<i){
                    withdrawal.setWithdrawalStatus(0);
                }

                if(userInfoQueryBoResult.getData().getUserInfoGrade()==i){
                    withdrawal.setWithdrawalStatus(2);
                }

                withdrawal.setUserGrade(i);


                if(i%5!=0){
                    BigDecimal b1 = new BigDecimal(money);
                    double f1 = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();


                    //截取小数点后两位
                    BigDecimal bigDecimal=new BigDecimal(f1);
                    withdrawal.setWithdrawalAmount(bigDecimal);
                    //如果用户等级大于人物等级 说明前面的都已经提现了
                }

                if(i%5==0){
                    money=money+0.1;


                    BigDecimal b1 = new BigDecimal(money);
                    double f1 = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                    BigDecimal bigDecimal=new BigDecimal(f1);

                    withdrawal.setWithdrawalAmount(bigDecimal);
                }


                list.add(withdrawal);
            }

            baseMapper.insertWithdrawalData(list);
        }

        LambdaQueryWrapper<Withdrawal> queryWrapper1 = new LambdaQueryWrapper<Withdrawal>()
                .eq(Withdrawal::getUserId, user.getId());

        List<Withdrawal> withdrawals1 = baseMapper.selectList(queryWrapper);


        map.put("withdrawals1",withdrawals1);
        System.out.println("userInfoQueryBoResult.getData().getUserInfoPacketBalance() = " + userInfoQueryBoResult.getData().getUserInfoPacketBalance());
        //余额
        map.put("balance",userInfoQueryBoResult.getData().getUserInfoPacketBalance());

        return map;
    }

    @Override
    public Message withdrawal(Long id) {
        Message message=new Message();


        LambdaQueryWrapper<Withdrawal> queryWrapper1 = new LambdaQueryWrapper<Withdrawal>()
                .eq(Withdrawal::getId , id);
        Withdrawal withdrawal = baseMapper.selectOne(queryWrapper1);

        Result<UserInfoQueryBo> userInfoQueryBoResult = userFeignClient.queryUser(localUser.getUser().getId());

        if(userInfoQueryBoResult.getData().getUserInfoGrade()>=withdrawal.getUserGrade()){
            message.setMsg("提现成功");
        }

        return message;
    }


}
