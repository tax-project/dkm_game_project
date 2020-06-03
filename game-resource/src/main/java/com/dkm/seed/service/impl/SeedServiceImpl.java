package com.dkm.seed.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.attendant.dao.AttendantMapper;
import com.dkm.attendant.entity.vo.User;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.UserFeignClient;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.knapsack.domain.bo.IncreaseUserInfoBO;
import com.dkm.land.dao.LandMapper;
import com.dkm.land.entity.vo.Message;
import com.dkm.land.entity.vo.UserLandUnlock;
import com.dkm.seed.dao.SeedMapper;
import com.dkm.seed.dao.UserLandUnlockMapper;
import com.dkm.seed.entity.LandSeed;
import com.dkm.seed.entity.Seed;
import com.dkm.seed.entity.vo.*;
import com.dkm.seed.service.ISeedService;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: dkm_game
 * @DESCRIPTION:
 * @USER: 刘梦祺
 * @DATE: 2020/5/11 16:17
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SeedServiceImpl implements ISeedService {
    @Autowired
    private SeedMapper seedMapper;

    @Autowired
    private LocalUser localUser;

    @Autowired
    private AttendantMapper attendantMapper;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private UserLandUnlockMapper userLandUnlockMapper;

    @Autowired
    private LandMapper landMapper;




    /**
     * 根据用户id得到种子
     */
    @Override
    public List<SeedPlantUnlock> queryUserIdSeed() {
        UserLoginQuery user = localUser.getUser();
        //查看用户是否是新用户
        List<SeedUnlock> seedUnlocks = seedMapper.queryIsById(user.getId());
        if(seedUnlocks.size()==0){
            List<SeedUnlock> seedUnlocks1=new ArrayList<>();
            List<Seed> attenDants = seedMapper.selectList(null);
            if(attenDants.size()==0) {
               throw new ApplicationException(CodeType.PARAMETER_ERROR,"没有种子");
            }
            for (int j = 0; j < attenDants.size(); j++) {
                System.out.println("attenDants = " + attenDants);
                SeedUnlock seedUnlock=new SeedUnlock();
                seedUnlock.setPuId(idGenerator.getNumberId());
                seedUnlock.setUserId(user.getId());
                //得到总需要解锁的次数
                double pow = Math.pow(Math.ceil(attenDants.get(j).getSeedGrade() / 2.0), 2);
                Integer powInteger = Integer.valueOf((int) pow);
                seedUnlock.setSeedAllUnlock(powInteger);
                seedUnlock.setSeedId(attenDants.get(j).getSeedId());
                seedUnlocks1.add(seedUnlock);
            }
            //增加新用户进来和用户对应的种子
            seedMapper.insertSeedUnlock(seedUnlocks1);
        }
        List<SeedPlantUnlock> seeds = seedMapper.queryUserIdSeed(user.getId());

        for (int i = 0; i < seeds.size(); i++) {
            //种植所获得的经验
            double experience = Math.pow(seeds.get(i).getSeedGrade(), 2 / 5.0) * 100;
            Integer experienceInteger = Integer.valueOf((int) experience);

            //种植一次所获得的金币
            double userGold  = Math.pow(seeds.get(i).getSeedGrade(), 2)*50 +500;
            Integer userGoldInteger = Integer.valueOf((int) userGold);
            seeds.get(i).setSeedExperience(experienceInteger);
            seeds.get(i).setSeedGold(userGoldInteger);
        }
        return seeds;
    }
    /**
     * 根据种子id得到种子
     *
     */
    @Override
    public SeedDetailsVo querySeedById(Integer seeId) {
        UserLoginQuery user = localUser.getUser();
        SeedDetailsVo seedDetailsVo = seedMapper.querySeedById(seeId, user.getId());
        //int sum=(int)Math.ceil(seedDetailsVo.getSeedGrade()/10.00)*10;
        seedDetailsVo.setPrestige(18);
        seedDetailsVo.setUnlockFragmentedGoldCoins(seedDetailsVo.getSeedGrade()*1000);
        return seedDetailsVo;
    }



    /**
     * 解锁植物
     * unlockmoeny 解锁金额
     *  grade      种子等级
     * seedid     种子id
     * @seedPresentUnlock 种子当前解锁进度
     * seedPresentAggregate 种子当前解锁进度
     * @return
     */
    @Override
    public Message unlockPlant(SeedVo seedVo) {
        UserLoginQuery user = localUser.getUser();
        //得到用户金币
        User user1 = attendantMapper.queryUserReputationGold(user.getId());
        if(user1.getUserInfoGold()<seedVo.getUnlockMoney()){
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "金币不足");
        }
        Message message=new Message();
        //如果当前进度等于总进度 则解锁种子 修改种子状态
        Integer seedPresentUnlock = seedVo.getSeedPresentUnlock()+1;
        if(seedPresentUnlock.equals(seedVo.getSeedPresentAggregate())){
            seedMapper.updateSeedPresentUnlock(user.getId(),seedVo.getSeedId(),null,1);
        }
            //种子等级除以10 得出声望
            //等级余10大于0则进一
            //int sum=(int)Math.ceil(seedVo.getGrade()/10.00);
            //修改当前种子解锁进度
            seedMapper.updateSeedPresentUnlock(user.getId(),seedVo.getSeedId(),seedVo.getSeedPresentUnlock(),null);
            //修改用户的金币和声望
            int i= seedMapper.uploadUnlockMoneyAndPrestige(seedVo.getUnlockMoney(), 18, user.getId());
            if(i<=0){
                throw new ApplicationException(CodeType.PARAMETER_ERROR, "解锁碎片异常");
            }
            message.setMsg("解锁碎片成功");
            return message;
    }
    /**
     * 种植种子
     *
     */
    @Override
    public void queryAlreadyPlantSeed(SeedPlantVo seedPlantVo) {

        Map<String,Object> map=new HashMap<>();
        List<LandSeed> list=new ArrayList<>();
        //得到用户token信息
        UserLoginQuery user = localUser.getUser();
        User user1 = attendantMapper.queryUserReputationGold(user.getId());
        if(user1.getUserInfoGold()<seedPlantVo.getSeedGold()){
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "金币不足");
        }

        //表示有新的种子种植解锁 种植
      /*  if(seedPlantVo.getStatus()==1){
            //根据用户查询解锁的土地
            List<UserLandUnlock> userLandUnlocks = landMapper.queryUnlockLand(localUser.getUser().getId());

        }*/

        //根据用户查询解锁的土地
        List<UserLandUnlock> userLandUnlocks = landMapper.queryUnlockLand(localUser.getUser().getId());

        //种植时减去用户金币
        IncreaseUserInfoBO increaseUserInfoBO=new IncreaseUserInfoBO();
        //算出种植种子需要多少钱
        Integer gold=seedPlantVo.getSeedGold()*userLandUnlocks.size();
        increaseUserInfoBO.setUserId(user.getId());
        increaseUserInfoBO.setUserInfoGold(gold);
        //减少金币
        Result result = userFeignClient.cutUserInfo(increaseUserInfoBO);

        //计算种子成熟时间 得到秒数。等级的3次方除以2.0*20+60
        double ripetime = Math.pow(seedPlantVo.getSeedGrade(), 3 / 2.0) * 20 + 60;
        //将秒数转换成整数类型
        Integer integer = Integer.valueOf((int) ripetime);
        //得到时间戳转换成时间格式，最后得到种子成熟的时间
        LocalDateTime time2 =LocalDateTime.ofEpochSecond(System.currentTimeMillis()/1000+integer,0,ZoneOffset.ofHours(8));
        //循环用户解锁土地，解锁多少多少土地 种植多少种子
        for (int i = 0; i < userLandUnlocks.size(); i++) {
            LandSeed landSeed=new LandSeed();
            //生成主键id
            landSeed.setLeId(idGenerator.getNumberId());
            //土地编号
            landSeed.setLaNo(userLandUnlocks.get(i).getLaNo());
            //种子id
            landSeed.setSeedId(seedPlantVo.getSeedId());
            //根据token得到用户id
            landSeed.setUserId(user.getId());
            //结束时间
            landSeed.setPlantTime(time2);
            list.add(landSeed);
        }
        //增加要种植种子的信息和用户信息
        int i = seedMapper.addPlant(list);
        if(i<=0){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"种植异常");
        }

    }

    @Override
    public int updateUser(UserInIf userInIf) {
        System.out.println("userInIf = " + userInIf.getUserInfoNowExperience()+"======"+userInIf.getUserInfoNextExperience());
        //得到用户token信息
        UserLoginQuery user = localUser.getUser();
        //得到用户已经种植的数据
        List<LandYesVo> landYesVos = seedMapper.queryAlreadyPlantSd(localUser.getUser().getId());
        //当前时间必须大于等于种植种植结束时间 才能收取
            if(System.currentTimeMillis()/1000>=landYesVos.get(0).getPlantTime().toEpochSecond(ZoneOffset.of("+8"))){
                //根据用户查询解锁的土地
                List<UserLandUnlock> userLandUnlocks = landMapper.queryUnlockLand(localUser.getUser().getId());

                //种植所获得的经验
                double experience = Math.pow(userInIf.getSeedGrade(), 2 / 5.0) * 100*userLandUnlocks.size();
                Integer experienceInteger = Integer.valueOf((int) experience);

                System.out.println("experienceInteger ====== " + experienceInteger);

                //种植一次所获得的金币
                double userGold  = Math.pow(userInIf.getSeedGrade(), 2)*50 +1000*userLandUnlocks.size();
                Integer userGoldInteger = Integer.valueOf((int) userGold);
                System.out.println("userInIf = " + userInIf.getUserInfoNowExperience()+"======"+userInIf.getUserInfoNextExperience());

                //判断当前经验是否等级下一级的等级 如果等于等级加一
                if(userInIf.getUserInfoNowExperience()+experienceInteger>=userInIf.getUserInfoNextExperience()){

                    userInIf.setUserInfoNowExperience(userInIf.getUserInfoNextExperience()+experienceInteger);
                    //当前经验减去总经验
                    userInIf.setUserInfoNowExperience(userInIf.getUserInfoNowExperience()-userInIf.getUserInfoNextExperience());
                    Result<UserInfoQueryBo> userInfoQueryBoResult = userFeignClient.queryUser(user.getId());

                    //算出下一级的总经验
                    double v = (((userInfoQueryBoResult.getData().getUserInfoGrade() - 1) +
                                ((userInfoQueryBoResult.getData().getUserInfoGrade() - 1) *
                                 (userInfoQueryBoResult.getData().getUserInfoGrade() - 2) * 2) / 2.0) * 100) + 600;

                    Integer nextExperience = Integer.valueOf((int) v);
                    System.out.println("nextExperience = " + nextExperience);
                    userInIf.setUserInfoNextExperience(nextExperience);
                    userInIf.setUserGold(userGoldInteger);
                    //随便传值 sql语句只是加了1
                    userInIf.setSeedGrade(userInIf.getUserGold()+1);
                    userInIf.setUserId(user.getId());

                    //修改用户信息
                    int i = seedMapper.updateUser(userInIf);
                    Result<UserInfoQueryBo> userInfoQueryBoResults = userFeignClient.queryUser(user.getId());
                    //每三级解锁一块土地
                    if(userInfoQueryBoResults.getData().getUserInfoGrade()==3 ||userInfoQueryBoResults.getData().getUserInfoGrade()==6
                            || userInfoQueryBoResults.getData().getUserInfoGrade()==9 ||userInfoQueryBoResults.getData().getUserInfoGrade()==12 ||userInfoQueryBoResults.getData().getUserInfoGrade()==15 ||
                            userInfoQueryBoResults.getData().getUserInfoGrade()==18||userInfoQueryBoResults.getData().getUserInfoGrade()==21
                            ||userInfoQueryBoResults.getData().getUserInfoGrade()==24){

                        List<UserLandUnlock> userLandUnlocks1 = landMapper.queryNotUnlocked(user.getId());
                        if(userLandUnlocks1.get(0).getLaStatus()==0){
                            System.out.println("userLandUnlocks1 = " + "修改土地状态");
                            landMapper.updateStatus(user.getId(), userLandUnlocks1.get(0).getLaNo());
                        }
                    }
                } else{
                    userInIf.setUserGold(userGoldInteger);
                    userInIf.setUserInfoNowExperience(experienceInteger);
                    userInIf.setUserId(user.getId());
                    //修改用户信息
                    int i = seedMapper.updateUsers(userInIf);
                    if(i<=0){
                        log.info("收取时，修改用户信息失败");
                        throw new ApplicationException(CodeType.SERVICE_ERROR);
                    }

                }
                //收取种子后 删除当前用户土地种子的数据
                int i1 = seedMapper.deleteLandSeed(user.getId());
                if(i1<0){
                    throw new ApplicationException(CodeType.PARAMETER_ERROR,"收取种子异常");
                }
                return i1;
            }
                return 0;

    }

    @Override
    public List<SeedUnlockVo> queryAreUnlocked(Long userId) {
        //得到用户token信息
        return seedMapper.queryAreUnlocked(userId);
    }

    @Override
    public Result<UserInfoQueryBo> queryUserAll() {
        //得到用户token信息
        UserLoginQuery user = localUser.getUser();
        return userFeignClient.queryUser(user.getId());
    }

    @Override
    public Map<String,Object> queryAlreadyPlantSd() {
        Map<String,Object> map=new HashMap<>();
        //得到用户token信息
        UserLoginQuery user = localUser.getUser();
        List<LandYesVo> landYesVos = seedMapper.queryAlreadyPlantSd(user.getId());
        if(landYesVos.size()==0){
                return null;
        }else{
            for (int i = 0; i < landYesVos.size(); i++) {
                //种植结束时间装换成秒数
                long l1 = landYesVos.get(i).getPlantTime().toEpochSecond(ZoneOffset.of("+8"));
                landYesVos.get(i).setTime(l1);
            }
            map.put("landSeedVos",landYesVos);
        }

        return map;
    }


}
