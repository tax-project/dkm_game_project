package com.dkm.seed.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.feign.UserFeignClient;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.seed.dao.LandSeedMapper;
import com.dkm.seed.dao.SeedsFallMapper;
import com.dkm.seed.entity.LandSeed;
import com.dkm.seed.entity.SeedsFall;
import com.dkm.seed.entity.vo.GoldOrMoneyVo;
import com.dkm.seed.service.ISeedFallService;
import com.dkm.seed.vilidata.RandomUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/8 15:32
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SeedFallServiceImpl implements ISeedFallService {

    @Autowired
    private LocalUser localUser;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private LandSeedMapper landSeedMapper;

    @Autowired
    private RandomUtils randomUtils;

    @Autowired
    private SeedsFallMapper seedsFallMapper;


    @Override
    public List<GoldOrMoneyVo> seedDrop(Integer seedGrade) {

        List<GoldOrMoneyVo> GoldOrMoneyVolist=new ArrayList<>();

        UserLoginQuery user = localUser.getUser();

        Result<UserInfoQueryBo> userInfoQueryBoResult = userFeignClient.queryUser(user.getId());

        //查询已经种植的种子
        LambdaQueryWrapper<LandSeed> queryWrapper = new LambdaQueryWrapper<LandSeed>()
                .eq(LandSeed::getUserId, user.getId())
                .eq(LandSeed::getLeStatus, 1);

        List<LandSeed> landSeedList = landSeedMapper.selectList(queryWrapper);

        if(landSeedList.size()==0){
            return null;
        }

        Integer gold=0;

        double money=0;

        List<SeedsFall> list=new ArrayList<>();

        SeedsFall seedsFall=null;

        for (LandSeed seed : landSeedList) {

            GoldOrMoneyVo goldOrMoneyVo=new GoldOrMoneyVo();

            seedsFall=new SeedsFall();
            seedsFall.setId(seed.getId());
            seedsFall.setSeedId(seed.getSeedId());

            //true掉落金币   false 没有金币掉落
            boolean dropCoins = randomUtils.probabilityDroppingGold(seedGrade);
            if(dropCoins){
                double userGold  = Math.pow(userInfoQueryBoResult.getData().getUserInfoGrade(), 2)*50 +1000;
                Integer userGoldInteger = (int) userGold;
                gold = randomUtils.NumberCoinsDropped(userGoldInteger,seed.getPlantTime().toEpochSecond(ZoneOffset.of("+8")));

                seedsFall.setDropCoins(gold);

                goldOrMoneyVo.setGold(gold);

            }

            //true 掉落红包   false 没有红包掉落
            boolean produceGoldRed = randomUtils.isProduceGoldRed(userInfoQueryBoResult.getData().getUserInfoGrade());
            if(produceGoldRed){
                money = randomUtils.NumberRedPacketsDropped();

                seedsFall.setDropRedEnvelope(money);

                goldOrMoneyVo.setMoney(money);

            }

            list.add(seedsFall);

            GoldOrMoneyVolist.add(goldOrMoneyVo);

        }

        //如果金币和红包都不为空 则添加
        if(seedsFall.getDropCoins() != null && seedsFall.getDropRedEnvelope()!= null){
            seedsFallMapper.insertSeedDropGoldOrRedEnvelopes(list);
        }


        return GoldOrMoneyVolist;
    }

    @Override
    public List<Double> redBagDroppedSeparately(Double money) {
        List<Double> list=new ArrayList<>();

        double sta=0;

        double end=0;

        //掉落次数
        /**
         * 一分钟
         * 两秒钟掉一次
         * 得出一分钟掉落多少次
         */
        int numberDrops= 60 / 2;

        for (int i = 0; i < numberDrops; i++) {
            //截取小数点后两位
            BigDecimal b1 = new BigDecimal(money / numberDrops);
            double f1 = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

            end+= f1;

            sta= f1;

            list.add(sta);
        }

        String s = String.valueOf(end);

        String substring = s.substring(0,3);

        BigDecimal bd=new BigDecimal(substring);

        double v = bd.doubleValue();

        BigDecimal a=new BigDecimal(money);

        //得到差值放入集合 得到最后一个红包
       list.add(a.subtract(bd).doubleValue());

        return list;
    }


}
