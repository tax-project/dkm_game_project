package com.dkm.seed.service.impl;

import com.dkm.attendant.dao.AttendantMapper;
import com.dkm.attendant.entity.vo.User;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.UserFeignClient;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.land.entity.vo.Message;
import com.dkm.seed.dao.SeedMapper;
import com.dkm.seed.entity.LandSeed;
import com.dkm.seed.entity.Seed;
import com.dkm.seed.entity.vo.LandSeedVo;
import com.dkm.seed.entity.vo.SeedUnlock;
import com.dkm.seed.entity.vo.SeedVo;
import com.dkm.seed.entity.vo.UserInIf;
import com.dkm.seed.service.ISeedService;
import com.dkm.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: dkm_game
 * @DESCRIPTION:
 * @USER: 刘梦祺
 * @DATE: 2020/5/11 16:17
 */
@Service
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
    /**
     * 根据用户id得到种子（是否解锁）
     */
    @Override
    public List<Seed> queryUserIdSeed() {
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
                seedUnlocks1.get(j).setPuId(idGenerator.getNumberId());
                seedUnlocks1.get(j).setSeedId(attenDants.get(j).getSeedId());
                seedUnlocks1.get(j).setUserId(user.getId());
                seedUnlocks1.get(j).setSeedAllUnlock((attenDants.get(j).getSeedGrade() + 1) * 2);
            }
            seedMapper.insertSeedUnlock(seedUnlocks1);
        }
        List<Seed> seeds = seedMapper.queryUserIdSeed(user.getId());
        return seeds;
    }
    /**
     * 根据种子id得到种子
     *
     */
    @Override
    public Seed querySeedById(Integer seeId) {
        return seedMapper.selectById(seeId);
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
        if(seedVo.getSeedPresentUnlock().equals(seedVo.getSeedPresentAggregate())){
            seedMapper.updateSeedPresentUnlock(user.getId(),seedVo.getSeedId(),null,1);
        }
            //种子等级除以10 得出声望
            //等级余10大于0则进一
            int sum=(int)Math.ceil(seedVo.getGrade()/10.00);
            //修改当前种子解锁进度
            seedMapper.updateSeedPresentUnlock(user.getId(),seedVo.getSeedId(),seedVo.getSeedPresentUnlock(),null);
            //修改用户的金币和声望
            int i= seedMapper.uploadUnlockMoneyAndPrestige(seedVo.getUnlockMoney(), sum, user.getId());
            if(i<=0){
                throw new ApplicationException(CodeType.PARAMETER_ERROR, "解锁碎片异常");
            }
            message.setMsg("解锁碎片成功");
            return message;
    }
    /**
     * 种植种子
     * 查询已种植的种植
     *
     */
    @Override
    public List<LandSeedVo> queryAlreadyPlantSeed(LandSeed landSeed) {
        //得到用户token信息
        UserLoginQuery user = localUser.getUser();
        //计算种子成熟时间 得到秒数。等级的3次方除以2.0*20+60
        double ripetime = Math.pow(landSeed.getGrade(), 3 / 2.0) * 20 + 60;
        //将秒数转换成整数类型
        Integer integer = Integer.valueOf((int) ripetime);
        //得到时间戳
        Long timestamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        //得到时间戳转换成时间格式，最后得到种子成熟的时间
        LocalDateTime time2 =LocalDateTime.ofEpochSecond(timestamp/1000+integer,0,ZoneOffset.ofHours(8));
        //根据token得到用户id
        landSeed.setUserId(user.getId());
        landSeed.setPlantTime(time2);
        //增加要种植种子的信息和用户信息
        int i = seedMapper.addPlant(landSeed);
        if(i<=0){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"种植异常");
        }
        //查询种植的植物
        List<LandSeedVo> seeds = seedMapper.queryAlreadyPlantSd(user.getId());
        return seeds;
    }

    @Override
    public int updateUser(UserInIf userInIf) {
        //修改用户信息
        int i = seedMapper.updateUser(userInIf);
        if(i<0){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"收取种子异常");
        }
        //收取种子后 删除土地种子表中对应的数据
        int i1 = seedMapper.deleteLandSeed(userInIf.getUserId());
        return i1;
    }


}
