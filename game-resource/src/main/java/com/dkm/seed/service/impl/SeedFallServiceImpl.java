package com.dkm.seed.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.entity.websocket.MsgInfo;
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
import com.dkm.seed.entity.vo.moneyVo;
import com.dkm.seed.service.ISeedFallService;
import com.dkm.seed.vilidata.RandomUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static com.dkm.seed.vilidata.RandomUtils.*;

/**
 * @author MQ
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

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public List<GoldOrMoneyVo> seedDrop() {

        List<GoldOrMoneyVo> goldOrMoneyVos=new ArrayList<>();

        //查询已经种植的种子
        LambdaQueryWrapper<LandSeed> queryWrapper  = new LambdaQueryWrapper<LandSeed>()
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

            Result<UserInfoQueryBo> userInfoQueryBoResult = userFeignClient.queryUser(seed.getUserId());
            if(userInfoQueryBoResult.getCode()!=0){
                throw new ApplicationException(CodeType.SERVICE_ERROR,"feign异常");
            }

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
            if(seedsFall.getDropCoins()!=0 || seedsFall.getDropRedEnvelope()!=0.0 || seedsFall.getDropFallingFlowers()!=0){
                list.add(seedsFall);
                //将掉落的金币和红包存入数据库
                seedsFallMapper.insertSeedDropGoldOrRedEnvelopes(list);
            }
            //将值封装到vo
            goldOrMoneyVos.add(goldOrMoneyVo);

        }

        return goldOrMoneyVos;
    }



    @Override
    public void redBagDroppedSeparately() {
        UserLoginQuery user = localUser.getUser();

        //查询出种子首次产出的金钱
        List<moneyVo> moneyVos = baseMapper.queryMoney();
        log.info("aDouble{}",moneyVos);
        if(moneyVos.size()==0 || moneyVos==null){
            return;
        }



        //截取小数点后两位
        //钱除以他掉落的一个次数 就是每次掉落的钱

        for (moneyVo moneyVo : moneyVos) {
            BigDecimal b1 = new BigDecimal(moneyVo.getSeedProdred()/30);
            double f1 = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                MsgInfo msgInfo = new MsgInfo();
                msgInfo.setMsg(String.valueOf(f1));
                msgInfo.setType(13);
                msgInfo.setMsgType(1);
                msgInfo.setToId(moneyVo.getUserId());

                log.info("发送掉落通知...");
                rabbitTemplate.convertAndSend("game_event_notice", JSON.toJSONString(msgInfo));

        }







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
