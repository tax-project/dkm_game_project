package com.dkm.seed.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctc.wstx.util.DataUtil;
import com.dkm.config.RedisConfig;
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
import com.dkm.seed.entity.bo.SeedFallBO;
import com.dkm.seed.entity.vo.GoldOrMoneyVo;
import com.dkm.seed.entity.vo.SeedDetailsVo;
import com.dkm.seed.entity.vo.SeedsFallVo;
import com.dkm.seed.entity.vo.moneyVo;
import com.dkm.seed.service.IDropStatusService;
import com.dkm.seed.service.ISeedFallService;
import com.dkm.seed.service.ISeedService;
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
import java.time.temporal.ChronoUnit;
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
    private ISeedService seedService;

    @Autowired
    private RedisConfig redisConfig;

    private String seedRedis = "REDIS::SEED::";

    @Override
    public SeedDropBO seedDrop(Integer userInfoGrade, Integer seedGrade) {

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

        //随机掉落
        SeedDropBO result = new SeedDropBO();

        List<Long> list=new ArrayList<>();
        //landSeeds不等于null，修改状态为2
        for (LandSeed landSeed : landSeeds) {
            if(System.currentTimeMillis()/1000 >= landSeed.getPlantTime().toEpochSecond(ZoneOffset.of("+8"))){
                //修改种子状态为2
                list.add(landSeed.getId());
            }

            if (dropStatus != null) {
                Long timeNumber = landSeed.getTimeNumber();
                Integer number = timeNumber.intValue();
                int newSeed = number - dropStatus.getMuchNumber();

                if (newSeed <= 0) {
                    return null;
                }
            }

            //如果是新种子  掉落红包直接返回
            if (landSeed.getNewSeedIs() == 1) {
                //新种子
                SeedDetailsVo vo = seedService.querySeedById(landSeed.getSeedId());

                Integer seedProdred = vo.getSeedProdred();

                result.setRedIsFail(1);
                result.setRedNumber(seedProdred.doubleValue());

                //如果是新种子，修改种子状态
                if (landSeed.getNewSeedIs() == 1) {
                    //修改成0
                    LandSeed land = new LandSeed();
                    land.setId(land.getId());
                    land.setNewSeedIs(0);
                    int updateById = landSeedMapper.updateById(land);
                }

                return result;
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

        //掉落红包
        boolean red = randomUtils.isProduceGoldRed(userInfoGrade);
        double redPacketsDropped = 0.00;
        if (red) {
            redPacketsDropped = randomUtils.numberRedPacketsDropped();

            if (redPacketsDropped == 0.0) {
                result.setRedIsFail(0);
            } else {
                result.setRedIsFail(1);
                result.setRedNumber(redPacketsDropped);
            }
        } else {
            //不掉落红包
            result.setRedIsFail(0);
        }

        //掉落金币
        boolean droppingGold = randomUtils.probabilityDroppingGold(seedGrade);
        Integer dropped = 0;
        if (droppingGold) {
            //掉落金币成功
            dropped = randomUtils.numberCoinsDropped();
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

        Object string = redisConfig.getString(seedRedis + user.getId());

        SeedFallBO bo = new SeedFallBO();

        if (string == null) {
            //第一次添加
            bo.setRedPacketsDropped(redPacketsDropped);
            bo.setDropped(dropped);
            bo.setFallingNumber(1);
            redisConfig.setString(seedRedis + user.getId(), bo);
        } else {
            //不是第一次添加
            SeedFallBO seedFallBO = (SeedFallBO) string;
            bo.setRedPacketsDropped(seedFallBO.getRedPacketsDropped() + redPacketsDropped);
            bo.setDropped(seedFallBO.getDropped() + dropped);
            bo.setFallingNumber(seedFallBO.getFallingNumber() + 1);
            redisConfig.setString(seedRedis + user.getId(), bo);
        }

        return result;
    }

    @Override
    public List<SeedDropBO> redBagDroppedSeparately(Long seedId, Integer userInfoGrade, Integer seedGrade) {

        UserLoginQuery user = localUser.getUser();

        //先查询有没有种植
        LambdaQueryWrapper<LandSeed> wrapper = new LambdaQueryWrapper<LandSeed>()
              .eq(LandSeed::getUserId, user.getId())
              .eq(LandSeed::getSeedId, seedId);

        List<LandSeed> landSeeds = landSeedMapper.selectList(wrapper);
        //查询种植的土地块数
        if (landSeeds == null || landSeeds.isEmpty()) {
            return null;
        }
        //默认不是新种子
        int newSeed = 0;

        //查询之前掉落的次数
        DropStatus dropStatus = dropStatusService.queryDropStatus(user.getId());
        List<SeedDropBO> list = new ArrayList<>();

        if (dropStatus == null) {
            return null;
        }

        for (LandSeed landSeed : landSeeds) {
            //根据时间做判断
            LocalDateTime now = LocalDateTime.now();

            long until = now.until(landSeed.getPlantTime(), ChronoUnit.MINUTES);

            Long much;
            if (until <= 0) {
                much = landSeed.getTimeNumber();
            } else {
                //还没到成熟时间
               much = landSeed.getTimeNumber() - until;
            }

            if (landSeed.getLeStatus() == 1) {
                //种植的种子
                if (landSeed.getNewSeedIs() == 1) {
                    //新种子
                    newSeed = 31 - dropStatus.getMuchNumber();
                } else {
                    //不是新种子
                    //次数
                    Integer number = much.intValue();
                    newSeed = number - dropStatus.getMuchNumber();
                }

                if (newSeed <= 0) {
                    continue;
                }

                //根据次数算出要掉落的次数
                //根据次数循环返回给前端掉落的结果
                //循环得到前端返回的数据
                for (int i = 0; i < newSeed; i++) {
                    SeedDropBO seedDropBO = seedDrop(userInfoGrade, seedGrade);
                    if (seedDropBO != null) {
                        list.add(seedDropBO);
                    }
                }
            }
        }
        return list;
    }


}
