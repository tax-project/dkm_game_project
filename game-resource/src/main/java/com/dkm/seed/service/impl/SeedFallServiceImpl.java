package com.dkm.seed.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctc.wstx.util.DataUtil;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.entity.websocket.MsgInfo;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.UserFeignClient;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.seed.dao.LandSeedMapper;
import com.dkm.seed.dao.SeedMapper;
import com.dkm.seed.dao.SeedsFallMapper;
import com.dkm.seed.entity.DropStatus;
import com.dkm.seed.entity.LandSeed;
import com.dkm.seed.entity.SeedsFall;
import com.dkm.seed.entity.bo.SeedDropBO;
import com.dkm.seed.entity.vo.GoldOrMoneyVo;
import com.dkm.seed.entity.vo.SeedsFallVo;
import com.dkm.seed.entity.vo.moneyVo;
import com.dkm.seed.service.IDropStatusService;
import com.dkm.seed.service.ISeedFallService;
import com.dkm.seed.vilidata.RandomUtils;
import com.dkm.utils.DateUtils;
import com.dkm.utils.IdGenerator;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
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
    private RandomUtils randomUtils;

    @Autowired
    private IDropStatusService dropStatusService;

    @Autowired
    private LandSeedMapper landSeedMapper;

    @Autowired
    private IdGenerator idGenerator;

    @Override
    public SeedDropBO seedDrop(Integer userInfoGrade) {

        UserLoginQuery user = localUser.getUser();

        DropStatus dropStatus = dropStatusService.queryDropStatus(user.getId());

        LambdaQueryWrapper<LandSeed> wrapper=new LambdaQueryWrapper<LandSeed>()
                    .eq(LandSeed::getUserId,user.getId())
                    .eq(LandSeed::getLeStatus,1);
        List<LandSeed> landSeeds = landSeedMapper.selectList(wrapper);

        //如果landSeeds等于null 说明没有种植 直接返回空
        if(landSeeds.size()==0){
            return null;
        }

        List<Long> list=new ArrayList<>();
        //landSeeds不等于null，修改状态为2
        for (LandSeed landSeed : landSeeds) {
            if(System.currentTimeMillis()/1000 >= landSeed.getPlantTime().toEpochSecond(ZoneOffset.of("+8"))){
                //修改种子状态为2
                list.add(landSeed.getId());
            }
        }

        if(list.size()!=0){
            int i = landSeedMapper.updateSeedStatus(list);
            if(i<=0){
                log.info("批量修改错误");
                throw new ApplicationException(CodeType.SERVICE_ERROR);
            }
        }


        DropStatus data = new DropStatus();
        if (dropStatus == null) {
            //增加
            data.setEndTime(LocalDateTime.now());
            data.setMuchNumber(1);
            data.setUserId(user.getId());

            dropStatusService.dropStatusUpdate(data);
        } else {
            //修改
            data.setUserId(user.getId());
            data.setMuchNumber(dropStatus.getMuchNumber() + 1);
            data.setEndTime(LocalDateTime.now());
            data.setId(dropStatus.getId());
            dropStatusService.dropStatusUpdate(data);
        }

        //随机掉落
        SeedDropBO result = new SeedDropBO();
        //掉落红包
        boolean red = randomUtils.isProduceGoldRed(userInfoGrade);

        if (red) {
            double redPacketsDropped = randomUtils.numberRedPacketsDropped();
            result.setRedIsFail(1);
            result.setRedNumber(redPacketsDropped);
        } else {
            //不掉落红包
            result.setRedIsFail(0);
        }

        //掉落金币
        boolean droppingGold = randomUtils.probabilityDroppingGold(userInfoGrade);

        if (droppingGold) {
            //掉落金币成功
            Integer dropped = randomUtils.numberCoinsDropped();
            result.setGoldIsFail(1);
            result.setGoldNumber(dropped);
        } else {
            //不掉落金币
            result.setGoldIsFail(0);
        }

        //掉落花
        Boolean aBoolean = randomUtils.fallingRandom();

        if (aBoolean) {
            //掉落花成功
            result.setFallingIsFail(1);
            result.setFallingNumber(1);
        } else {
            //掉落花失败
            result.setFallingIsFail(0);
        }

        return result;
    }

    @Override
    public List<SeedDropBO> redBagDroppedSeparately(Long seedId, Integer userInfoGrade) {

        UserLoginQuery user = localUser.getUser();

        //先查询有没有种植
        LambdaQueryWrapper<LandSeed> wrapper = new LambdaQueryWrapper<LandSeed>()
              .eq(LandSeed::getUserId, user.getId())
              .eq(LandSeed::getSeedId, seedId);

        List<LandSeed> landSeeds = landSeedMapper.selectList(wrapper);

        //查询种植的土地块数

        //默认不是新种子
        int newSeed = 0;

        //查询之前掉落的次数
        DropStatus dropStatus = dropStatusService.queryDropStatus(user.getId());

        List<SeedDropBO> list = new ArrayList<>();

        for (LandSeed landSeed : landSeeds) {
            if (landSeed.getLeStatus() == 0 || landSeed.getLeStatus() == 1) {
                //种植的种子
                if (landSeed.getNewSeedIs() == 1) {
                    //新种子
                    newSeed = 31 - dropStatus.getMuchNumber();

                } else {
                    //不是新种子
                    //次数
                    Long timeNumber = landSeed.getTimeNumber();
                    Integer number = timeNumber.intValue();
                    newSeed = number - dropStatus.getMuchNumber();
                }
            }

            //根据次数算出要掉落的次数
            //根据次数循环返回给前端掉落的结果
            //循环得到前端返回的数据
            for (int i = 0; i < newSeed; i++) {
                SeedDropBO seedDropBO = seedDrop(userInfoGrade);
                list.add(seedDropBO);
            }

        }

        return list;
    }


}
