package com.dkm.seed.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.UserFeignClient;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.seed.dao.LandSeedMapper;
import com.dkm.seed.dao.SeedsFallMapper;
import com.dkm.seed.entity.LandSeed;
import com.dkm.seed.entity.SeedsFall;
import com.dkm.seed.entity.vo.GoldOrMoneyVo;
import com.dkm.seed.entity.vo.SeedsFallVo;
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

import static com.dkm.seed.vilidata.RandomUtils.*;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/8 15:32
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SeedFallServiceImpl extends ServiceImpl<SeedsFallMapper, SeedsFall> implements ISeedFallService {

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
    public List<GoldOrMoneyVo> seedDrop() {

        List<GoldOrMoneyVo> goldOrMoneyVos=new ArrayList<>();

        //查询已经种植的种子
        LambdaQueryWrapper<LandSeed> queryWrapper  = new LambdaQueryWrapper<LandSeed>()
                .eq(LandSeed::getLeStatus, 1);

        List<LandSeed> landSeedList = landSeedMapper.selectList(queryWrapper);

        if(landSeedList.size()==0){
           throw new ApplicationException(CodeType.SERVICE_ERROR,"当前没有用户种植");
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

            Result<UserInfoQueryBo> userInfoQueryBoResult = userFeignClient.queryUser(seed.getUserId());

            //true掉落金币   false 没有金币掉落
            boolean dropCoins = randomUtils.probabilityDroppingGold(seed.getSeedId());
            if(dropCoins){
                gold = randomUtils.NumberCoinsDropped(seed.getPlantTime().toEpochSecond(ZoneOffset.of("+8")));

                seedsFall.setDropCoins(gold);

                goldOrMoneyVo.setGold(gold);

            }

            //true 掉落红包   false 没有红包掉落
            boolean produceGoldRed =randomUtils.isProduceGoldRed(userInfoQueryBoResult.getData().getUserInfoGrade());
            if(produceGoldRed){
                //掉落的红包数量
                money =randomUtils.NumberRedPacketsDropped();

                seedsFall.setDropRedEnvelope(money);

                goldOrMoneyVo.setMoney(money);

            }

            //掉落花
            Integer integer =fallingFlowers();
            seedsFall.setDropFallingFlowers(integer);

            goldOrMoneyVo.setDropFallingFlowers(integer);

            /**
             * 将对象添加到集合  然后批量添加
             * 只要有掉落的数据不等于0 则添加到集合中
             */
            if(!seedsFall.getDropCoins().equals(0) || !seedsFall.getDropRedEnvelope().equals(0.0) || !seedsFall.getDropFallingFlowers().equals(0)){
                list.add(seedsFall);
            }
            //将值封装到vo
            goldOrMoneyVos.add(goldOrMoneyVo);

        }

        //将掉落的金币和红包存入数据库
        seedsFallMapper.insertSeedDropGoldOrRedEnvelopes(list);

        return goldOrMoneyVos;
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

        BigDecimal a=new BigDecimal(money);

        /**
         * 得到差值放入集合 得到最后一个红包
         * subtract相减
         */
        list.add(a.subtract(bd).doubleValue());

        return list;
    }

    /**
     * 查询已掉落的数据
     * @return
     */
    @Override
    public List<SeedsFallVo> queryDroppedItems() {
        List<SeedsFallVo> seedsFallVos = baseMapper.queryDroppedItems(localUser.getUser().getId());
        return seedsFallVos;
    }


    /**
     * 修改种子状态为2 待收取
     * @param list
     * @return
     */
    @Override
    public int updateLeStatus(List<Long> list) {
        return baseMapper.updateLeStatus(list);
    }


}
