package com.dkm.attendant.service.Impl;


import com.dkm.attendant.dao.AttendantMapper;
import com.dkm.attendant.entity.AttenDant;
import com.dkm.attendant.entity.AttendantUser;
import com.dkm.attendant.entity.bo.*;
import com.dkm.attendant.entity.vo.*;
import com.dkm.attendant.service.IAttendantService;
import com.dkm.attendant.service.IAttendantUserService;
import com.dkm.config.RedisConfig;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.ParamBo;
import com.dkm.entity.bo.UserHeardUrlBo;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.entity.vo.AttendantWithUserVo;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.BaseFeignClient;
import com.dkm.feign.UserFeignClient;
import com.dkm.feign.entity.PetsDto;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.knapsack.domain.TbEquipmentKnapsack;
import com.dkm.knapsack.domain.bo.IncreaseUserInfoBO;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVo;
import com.dkm.knapsack.service.ITbEquipmentKnapsackService;
import com.dkm.produce.entity.vo.AttendantPutVo;
import com.dkm.produce.service.IProduceService;
import com.dkm.utils.DateUtils;
import com.dkm.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: dkm_game
 * @DESCRIPTION:
 * @USER: 刘梦祺
 * @DATE: 2020/5/11 14:06
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AttendantServiceImpl implements IAttendantService {
    @Autowired
    private AttendantMapper attendantMapper;

    @Autowired
    private LocalUser localUser;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private ITbEquipmentKnapsackService iTbEquipmentKnapsackService;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private BaseFeignClient baseFeignClient;

    @Autowired
    private IAttendantUserService attendantUserService;

    @Autowired
    private RedisConfig redisConfig;

    @Autowired
    private IProduceService produceService;

    private String redisLock = "REDIS::LOCK:ATTENDANT";

    /**
     * 获取用户抓到的跟班信息
     * @return
     */
    @Override
    public List<AttUserAllInfoVo> queryThreeAtt() {
        //得到用户登录的token信息
        UserLoginQuery query = localUser.getUser();
        //查询到所有系统跟班
        List<AttUserAllInfoVo> list = attendantMapper.queryThreeAtt(query.getId(), 0);

        //查询到所有用户跟班
        List<AttUserAllInfoVo> list1 = attendantMapper.queryThreeAtt(query.getId(), 1);

        List<AttendantPutVo> outputList = produceService.queryOutput(query.getId());

        List<Long> longList = new ArrayList<>();
        for (AttUserAllInfoVo vo : list1) {
            longList.add(vo.getCaughtPeopleId());
        }

        //去查询用户的详细信息
        ParamBo bo = new ParamBo();
        bo.setList(longList);
        Result<List<UserHeardUrlBo>> listResult = userFeignClient.queryAllHeardByUserId(bo);

        if (listResult.getCode() != 0) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "feign有误");
        }

        List<UserHeardUrlBo> resultData = listResult.getData();

        Map<Long, UserHeardUrlBo> urlBoMap = resultData.stream().
              collect(Collectors.toMap(UserHeardUrlBo::getUserId, userHeardUrlBo ->
                    userHeardUrlBo
              ));

        List<AttUserAllInfoVo> collect = list1.stream().map(attUserAllInfoVo -> {
            AttUserAllInfoVo result = new AttUserAllInfoVo();
            BeanUtils.copyProperties(attUserAllInfoVo, result);
            result.setAtImg(urlBoMap.get(attUserAllInfoVo.getCaughtPeopleId()).getHeadUrl());
            result.setAtName(urlBoMap.get(attUserAllInfoVo.getCaughtPeopleId()).getNickName());
            result.setSysStatus(1);
            return result;
        }).collect(Collectors.toList());

        for (AttUserAllInfoVo vo : list) {
            vo.setSysStatus(0);
            collect.add(vo);
        }

        List<AttPutBo> userAtt = new ArrayList<>();
        List<AttPutBo> sysAtt1 = new ArrayList<>();
        List<AttPutBo> sysAtt2 = new ArrayList<>();
        List<AttPutBo> sysAtt3 = new ArrayList<>();
        for (AttUserAllInfoVo vo : collect) {
            for (AttendantPutVo putVo : outputList) {
                if (vo.getAId() == 0) {
                    //用户跟班
                    if (vo.getCaughtPeopleId().equals(putVo.getCaughtPeopleId())) {
                        AttPutBo attPutBo = new AttPutBo();
                        BeanUtils.copyProperties(putVo,attPutBo);
                        userAtt.add(attPutBo);
                        vo.setList(userAtt);
                    }
                }

                if (vo.getAId() == 1) {
                    AttPutBo attPutBo = new AttPutBo();
                    BeanUtils.copyProperties(putVo,attPutBo);
                    sysAtt1.add(attPutBo);
                    vo.setList(userAtt);
                }

                if (vo.getAId() == 2) {
                    AttPutBo attPutBo = new AttPutBo();
                    BeanUtils.copyProperties(putVo,attPutBo);
                    sysAtt2.add(attPutBo);
                    vo.setList(userAtt);
                }

                if (vo.getAId() == 3) {
                    AttPutBo attPutBo = new AttPutBo();
                    BeanUtils.copyProperties(putVo,attPutBo);
                    sysAtt3.add(attPutBo);
                    vo.setList(userAtt);
                }
            }
        }

        return collect;
    }



    /**
     * 获取用户声望和金币
     * @return
     */
    @Override
    public User queryUserReputationGold() {
        //得到用户登录的token信息
        UserLoginQuery query = localUser.getUser();
        User user = attendantMapper.queryUserReputationGold(query.getId());
        if(user==null){
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "获取用户声望和金币异常");
        }
        return user;
    }

    @Override
    public List<TbEquipmentKnapsackVo> selectUserIdAndFood() {
        //得到用户登录的token信息
        UserLoginQuery query = localUser.getUser();
        return iTbEquipmentKnapsackService.selectFoodId();
    }

    @Override
    public Map<String, Object> queryRandomUser() {
        //得到用户登录的token信息
        UserLoginQuery query = localUser.getUser();

        //随机返回9条数据
        Result<List<AttendantWithUserVo>> result = userFeignClient.listAttUser(query.getId());

        if (result.getCode() != 0) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "Feign有误");
        }

        //得到用户信息集合
        List<AttendantWithUserVo> userList = result.getData();

        //流化
        List<AttUserResultBo> attUserResultBoList = userList.stream().map(attendantWithUserVo -> {
            AttUserResultBo bo = new AttUserResultBo();
            BeanUtils.copyProperties(attendantWithUserVo, bo);
            bo.setAId(0L);
            bo.setSysStatus(1);
            return bo;
        }).collect(Collectors.toList());

        //得到系统跟班列表
        List<AttenDant> list = attendantMapper.selectList(null);

        List<AttendantBo> boList = new ArrayList<>();
        for (AttenDant attenDant : list) {
            AttendantBo bo = new AttendantBo();
            BeanUtils.copyProperties(attenDant,bo);
            //系统
            bo.setSysStatus(0);
            boList.add(bo);
        }

        Map<String, Object> map = new HashMap<>(2);
        map.put("sys",boList);
        map.put("userInfo",attUserResultBoList);

        return map;
    }
    /**
     * 解雇
     */
    @Override
    public void dismissal(Long caughtPeopleId, Long aId) {

        UserLoginQuery user = localUser.getUser();
        //解雇
        attendantMapper.dismissal(caughtPeopleId, aId);

        //删除对应的跟班产出物品
        produceService.deletePut (user.getId(),aId);
    }

    @Override
    public Map<String, Object> petBattle(Long caughtPeopleId) {
        //我方随机上场的宠物
        String myPet=null;

        //他方随机上场的宠物
        String hePet=null;

        //我方装备血量之和
        double myedLisfe=0;

        /**
         * 我方数据
         */
        //我方装备加成
        double myEquipmentBonus = 0;

        //得到最终我方的战力
        Integer myRipetime=0;

        //我方防御力
        double ourDefenses=0;

        //得到最终我方血量
        double ourHealth=0;

        /**
         * 他方数据
         */
        //得到最终他方的防御力
        double heDefense = 0;

        //他方装备加成
        double heEquipmentBonus = 0;

        //得到最终他方的战力
        int heRipetime1=0;

        //他方最终得到的血量
        double heEquipBonus=0;

        Map<String,Object> map=new HashMap<>();
        //得到用户登录的token信息
        UserLoginQuery query = localUser.getUser();
        //自己的信息
        Result<UserInfoQueryBo> userInfoQueryBoResult = userFeignClient.queryUser(query.getId());
        //对手用户信息
        Result<UserInfoQueryBo> userInfoQueryBoResultCaughtPeopleId = userFeignClient.queryUser(caughtPeopleId);

        //他方宠物信息
        Result<List<PetsDto>> petInfo1 = baseFeignClient.getPetInfo(caughtPeopleId);
        //随机获取他方宠物
        PetsDto hePetsDto = petInfo1.getData().get(new Random().nextInt(petInfo1.getData().size()));
        hePet=hePetsDto.getPetName();

        //得到装备信息
        List<TbEquipmentKnapsackVo> tbEquipmentKnapsackVos1 = iTbEquipmentKnapsackService.selectUserIdTwo(caughtPeopleId);
        //如果没有装备
        if(tbEquipmentKnapsackVos1.size()==0){
            //血量
            heEquipBonus=500;
            //他方装备防御力
            heDefense=0;
            //得到他方的战力
            heRipetime1=100;
        }else {
            for (int i = 0; i < tbEquipmentKnapsackVos1.size(); i++) {
                /**
                 * 属性加成 1就代表有加成 0代表没有加成
                 */
                if (tbEquipmentKnapsackVos1.get(i).getEdAttribute().intValue() == 1) {
                    // 1 为生命加成 2为才华加成
                    if (tbEquipmentKnapsackVos1.get(i).getEdType().intValue() == 1) {
                        //装备血量加上加成的装备血量
                        //得到最终的血量
                        heEquipBonus = heEquipBonus + tbEquipmentKnapsackVos1.get(i).getEdLife().intValue() * tbEquipmentKnapsackVos1.get(i).getEdTypevalue().doubleValue();
                    }
                    // 1 为生命加成 2 为才华加成
                    if (tbEquipmentKnapsackVos1.get(i).getEdType().intValue() == 2) {
                        //装备才华加上加成的装备才华
                        heEquipBonus = heEquipBonus + tbEquipmentKnapsackVos1.get(i).getEdDefense().intValue() * tbEquipmentKnapsackVos1.get(i).getEdTypevalue().doubleValue();
                    }
                }

                /**
                 * 问题： 没有加成我怎么知道他是血量还是才华
                 *
                 * 属性加成 1就代表有加成 0代表没有加成
                 *
                 */
                if (tbEquipmentKnapsackVos1.get(i).getEdAttribute().intValue() == 0) {
                    //没有加成的的话 直接将装备的生命赋值
                    heEquipBonus = heEquipBonus + tbEquipmentKnapsackVos1.get(i).getEdLife().intValue();
                }
/*
                double heEquipmentBonus = 0;*/
                /**
                 * 属性加成 1就代表有加成 0代表没有加成
                 * 如果有加成在判断是生命还是才华
                 */
                //他方装备加成
                if (tbEquipmentKnapsackVos1.get(i).getEdAttribute().intValue() == 1) {
                    // 1 为生命加成 2 为才华加成
                    if (tbEquipmentKnapsackVos1.get(i).getEdType().intValue() == 1) {
                        // 生命加成
                        heEquipmentBonus =heEquipmentBonus+ tbEquipmentKnapsackVos1.get(i).getEdTypevalue().doubleValue();
                    } else if(tbEquipmentKnapsackVos1.get(i).getEdType().intValue() == 2){
                        //才华加成
                        heEquipmentBonus =heEquipmentBonus+ tbEquipmentKnapsackVos1.get(i).getEdTypevalue().doubleValue();
                    }
                }

                /**
                 * 装备防御力*各个装备属性加成
                 */
                heDefense = heDefense + tbEquipmentKnapsackVos1.get(i).getEdDefense().doubleValue() * heEquipmentBonus;

                //我方方装备加成
                //double myEquipmentBonus = 0;
                if (myEquipmentBonus == 0) {
                    //我方装备加成
                    List<TbEquipmentKnapsackVo> tbEquipmentKnapsackVos = iTbEquipmentKnapsackService.selectUserIdTwo(query.getId());
                    /**
                     * 属性加成 1就代表有加成 0代表没有加成
                     * 如果有加成在判断是生命还是才华
                     */
                    if (tbEquipmentKnapsackVos.get(i).getEdAttribute().intValue() == 1) {
                        // 1 为生命加成 2 为才华加成
                        if (tbEquipmentKnapsackVos.get(i).getEdType().intValue() == 1) {
                            // 生命加成
                            myEquipmentBonus =myEquipmentBonus+tbEquipmentKnapsackVos.get(i).getEdTypevalue().doubleValue();
                        } else {
                            //才华加成
                            myEquipmentBonus =myEquipmentBonus+tbEquipmentKnapsackVos.get(i).getEdTypevalue().doubleValue();
                        }
                    }
                }
            }

            //得到他方的战力
            double heRipetime = StrictMath.pow(userInfoQueryBoResultCaughtPeopleId.getData().getUserInfoRenown(), 1/2.0)+
                    (userInfoQueryBoResultCaughtPeopleId.getData().getUserInfoRenown() * heEquipmentBonus - userInfoQueryBoResult.getData().getUserInfoRenown() + myEquipmentBonus);

           //得到最终他方的战力
            heRipetime1 = (int) heRipetime;
        }

        //我方宠物信息
        Result<List<PetsDto>> petInfo = baseFeignClient.getPetInfo(query.getId());
        //随机获取我方宠物
        PetsDto myPetsDto = petInfo.getData().get(new Random().nextInt(petInfo.getData().size()));
        myPet=myPetsDto.getPetName();

        //得到自己装备信息
        List<TbEquipmentKnapsackVo> tbEquipmentKnapsackVos = iTbEquipmentKnapsackService.selectUserIdTwo(query.getId());
        if(tbEquipmentKnapsackVos.size()==0){
            //血量
            ourHealth=500;
            //得到我方装备防御力
            ourDefenses=0;
            //得到我方战力
            myRipetime=100;
        }else{
            for (int i = 0; i < tbEquipmentKnapsackVos.size(); i++) {
                /**
                 * 属性加成 1就代表有加成 0代表没有加成
                 * 得到各个装备属性加成的值
                 */
                if (tbEquipmentKnapsackVos.get(i).getEdAttribute().intValue() == 1) {
                    // 1 为生命加成 2为才华加成
                    if (tbEquipmentKnapsackVos.get(i).getEdType().intValue() == 1) {
                        //装备血量加上加成的装备血量
                        ourHealth = ourHealth + tbEquipmentKnapsackVos.get(i).getEdLife().intValue() * tbEquipmentKnapsackVos.get(i).getEdTypevalue().doubleValue();
                    }
                    // 1 为生命加成 2 为才华加成
                    if (tbEquipmentKnapsackVos.get(i).getEdType().intValue() == 2) {
                        //装备才华加上加成的装备才华
                        ourHealth = ourHealth + tbEquipmentKnapsackVos.get(i).getEdDefense().intValue() * tbEquipmentKnapsackVos.get(i).getEdTypevalue().doubleValue();
                    }
                }

                /**
                 *
                 * 属性加成 1就代表有加成 0代表没有加成
                 * 如果没有加成直接拿到装备生命
                 */
                if (tbEquipmentKnapsackVos.get(i).getEdAttribute().intValue() == 0) {
                    //没有加成的的话 直接将装备的生命赋值
                    myedLisfe = myedLisfe + tbEquipmentKnapsackVos.get(i).getEdLife().intValue();
                }

                /**
                 * 属性加成 1就代表有加成 0代表没有加成
                 * 如果有加成在判断是生命还是才华
                 */
                //我方装备加成
                if (tbEquipmentKnapsackVos.get(i).getEdAttribute().intValue() == 1) {
                    // 1 为生命加成 2 为才华加成
                    if (tbEquipmentKnapsackVos.get(i).getEdType().intValue() == 1) {
                        // 生命加成
                        myEquipmentBonus =myEquipmentBonus+ tbEquipmentKnapsackVos.get(i).getEdTypevalue().doubleValue();
                    } else if(tbEquipmentKnapsackVos.get(i).getEdType().intValue() == 2){
                        //才华加成
                        myEquipmentBonus =myEquipmentBonus+ tbEquipmentKnapsackVos.get(i).getEdTypevalue().doubleValue();
                    }
                }

                //他方装备属性加成等于0 在查询一遍赋值
                if(heEquipBonus==0){
                     /**
                     * 属性加成 1就代表有加成 0代表没有加成
                     * 如果有加成在判断是生命还是才华
                     */
                    if (tbEquipmentKnapsackVos1.get(i).getEdAttribute().intValue() == 1) {
                        // 1 为生命加成 2 为才华加成
                        if (tbEquipmentKnapsackVos1.get(i).getEdType().intValue() == 1) {
                            // 生命加成
                            heEquipmentBonus =heEquipmentBonus+tbEquipmentKnapsackVos1.get(i).getEdTypevalue().doubleValue();
                        } else {
                            //才华加成
                            heEquipmentBonus =heEquipmentBonus+tbEquipmentKnapsackVos1.get(i).getEdTypevalue().doubleValue();
                        }
                    }
                }

                /**
                 * 装备防御力*各个装备属性加成
                 * 得到最终我方防御力
                 */
                ourDefenses = ourDefenses + tbEquipmentKnapsackVos.get(i).getEdDefense().doubleValue() * myEquipmentBonus;
            }
            //得到我方的战力
            double heRipetime = StrictMath.pow(userInfoQueryBoResult.getData().getUserInfoRenown().doubleValue(), 1/2.0)+
                    (userInfoQueryBoResult.getData().getUserInfoRenown().doubleValue() *myEquipmentBonus  - userInfoQueryBoResultCaughtPeopleId.getData().getUserInfoRenown().doubleValue() + heEquipmentBonus);
            //得到最终我方的战力
            myRipetime= (int) heRipetime;

        }

        //如果双方宠物相同 等级高的先动手
        if(myPet.equals(hePet)){
            if(myPetsDto.getPGrade()>hePetsDto.getPGrade()){
                //我方先动手
                map.put("status",0);
            }else{
                //他方先动手
                map.put("status",1);
            }
        }
        if ("老鼠".equals(myPet)) {
           if("大象".equals(hePet)){
               //我方先动手
               map.put("status",0);
           }else{
               //他方先动手
               map.put("status",1);
           }
        }else if("老虎".equals(myPet)){
            if("老鼠".equals(hePet)){
                map.put("status",0);
            }else{
                map.put("status",1);
            }
        }else if("大象".equals(myPet)){
            if("老虎".equals(hePet)){
                map.put("status",0);
            }else{
                map.put("status",1);
            }
        }

        map.put("userInfoQueryBoResult",userInfoQueryBoResult);
        map.put("userInfoQueryBoResultCaughtPeopleId",userInfoQueryBoResultCaughtPeopleId);
        //随机生成我方宠物信息
        map.put("myPetsDto",myPetsDto);
        //随机生成我他方宠物信息
        map.put("hePetsDto",hePetsDto);
        //我方血量             我方血量加上我方防御力得到最终血量
        map.put("ourHealth",ourHealth+ourDefenses);
        //他方血量
        map.put("ourHealth1",heEquipBonus+heDefense);
        //我方战力
        map.put("ourCapabilities",myRipetime);
        //System.out.println("他方战斗力 = " + otherForce);
        //他方战力
        map.put("heRipetime1",heRipetime1);
        return map;
    }


    @Override
    public AttUserVo addGraspFollowing(Long caughtPeopleId, Integer status, Long attendantId) {

        UserLoginQuery user = localUser.getUser();

        //查询该用户信息
        Result<UserInfoQueryBo> result = userFeignClient.queryUser(user.getId());

        if (result.getCode() != 0) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "用户feign错误");
        }

        AttUserVo vo = new AttUserVo();

        //根据用户Id查询所有跟班信息
        List<AttendantUser> list = attendantUserService.queryListByUserId(user.getId());

        //得到用户信息
        UserInfoQueryBo bo = result.getData();
        int number = 0;
        int size = 1;

        //3级解锁一个跟班
        for (int i = 1; i <= 6; i++) {

            if (bo.getUserInfoGrade() == i) {
                break;
            }

            if (bo.getUserInfoGrade() >= number && bo.getUserInfoGrade() < number + 3) {
                if (list.size() >= size) {
                    throw new ApplicationException(CodeType.SERVICE_ERROR, "该等级不能解锁");
                }
            }
            number += 3;
            size += 1;
        }

        if (list.size() >= 6) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "您最多只能抓6个跟班");
        }

        //推后12小时
        LocalDateTime time = LocalDateTime.now().plusHours(12);
        String s = DateUtils.formatDateTime(time);
        vo.setS(s);
        AttendantUser attendantUser=new AttendantUser();

        if (status == 1) {
            //抓用户跟班

            try {
                //加锁,保证原子性
                Boolean lock = redisConfig.redisLock(redisLock);

                if (!lock) {
                    throw new ApplicationException(CodeType.RESOURCES_NOT_FIND, "网络繁忙请稍后再试");
                }

                AttendantUser attendantUser1 = attendantUserService.queryOne(caughtPeopleId);

                if (attendantUser1 != null) {
                    //该用户已被抓，得到他主人的用户Id返回给前端,继续打
                    Long userId = attendantUser1.getUserId();
                    vo.setAId(0L);
                    vo.setCaughtPeopleId(userId);
                    vo.setStatus(1);
                    return vo;
                }

                //查询主人的跟班的用户id
                AttendantUser queryAttendantUser = attendantUserService.queryAttendantUser(caughtPeopleId, user.getId());

                long id = idGenerator.getNumberId();
                if (queryAttendantUser != null) {
                    //说明这是跟主人在打架
                    //将主人的跟班id添加
                    attendantUser.setAtuId(id);
                    attendantUser.setAttendantId(0L);
                    attendantUser.setCaughtPeopleId(queryAttendantUser.getCaughtPeopleId());
                    attendantUser.setUserId(user.getId());
                    attendantUser.setEndDate(s);
                    attendantUserService.insert(attendantUser);
                    //代表抢用户跟班成功
                    vo.setStatus(0);
                    return vo;
                }
                //跟没有主人的用户打架
                attendantUser.setAtuId(id);
                attendantUser.setAttendantId(0L);
                attendantUser.setCaughtPeopleId(caughtPeopleId);
                attendantUser.setUserId(user.getId());
                attendantUser.setEndDate(s);
                attendantUserService.insert(attendantUser);
                //代表抢用户跟班成功
                vo.setStatus(0);
                return vo;
            } finally {
                redisConfig.deleteLock(redisLock);
            }

        }

        //抓系统跟班
        attendantUser.setAtuId(idGenerator.getNumberId());
        if (attendantId == null) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "系统跟班需要传跟班id");
        }
        attendantUser.setAttendantId(attendantId);
        attendantUser.setCaughtPeopleId(0L);
        attendantUser.setUserId(user.getId());
        attendantUser.setEndDate(s);
        attendantUserService.insert(attendantUser);
        vo.setStatus(0);
        return vo;
    }


    /**
     * 收取
     * @param attId
     * @return
     */
    @Override
    public Map<String, Object> collect(Long attId, Long attUserId) {
//        long exp1 = System.currentTimeMillis() / 1000 + 43200;

        AttendantUser attUser = attendantUserService.queryAttUser(attUserId);

        if (attUser == null) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "attUserId有误");
        }

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime localDateTime = DateUtils.parseDateTime(attUser.getEndDate());

        //得到6小时之前的时间
        LocalDateTime time = localDateTime.minusHours(6);

        long until = now.until(time, ChronoUnit.SECONDS);

        if (until >= 0) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "现在不能收取");
        }

        UserLoginQuery user = localUser.getUser();
        //得到产出的物品集合
        List<CollectResultBo> list = attendantMapper.collect(user.getId(), attId);

        List<Long> idList = new ArrayList<>();

        for (CollectResultBo bo : list) {
            //如果是金币  加入用户的总金币
            if (bo.getGoodType() == 0) {
                IncreaseUserInfoBO increaseUserInfoBO=new IncreaseUserInfoBO();
                increaseUserInfoBO.setUserInfoGold(bo.getGoodNumber());
                increaseUserInfoBO.setUserId(user.getId());
                //修改用户金币
                userFeignClient.increaseUserInfo(increaseUserInfoBO);
            } else {
                //穿戴物品
                TbEquipmentKnapsack tbEquipmentKnapsack=new TbEquipmentKnapsack();
                tbEquipmentKnapsack.setFoodId(bo.getGoodId());
                tbEquipmentKnapsack.setFoodNumber(bo.getGoodNumber());
                tbEquipmentKnapsack.setTekIsva(1);
                tbEquipmentKnapsack.setTekDaoju(bo.getGoodType());
                tbEquipmentKnapsack.setTekMoney(50);
                tbEquipmentKnapsack.setTekSell(2);
                //添加食物到背包
                iTbEquipmentKnapsackService.addTbEquipmentKnapsack(tbEquipmentKnapsack);
            }

            idList.add(bo.getProduceId());
        }

        //返回产出的数据以及12小时后的时间给前端
        LocalDateTime dateTime = LocalDateTime.now().plusHours(12);

        String expTime = DateUtils.formatDateTime(dateTime);

        //修改产出表的状态
        if (null != idList && idList.size() > 0) {
            Integer integer = attendantMapper.updateProduceStatus(idList);

            if (integer <= 0) {
                throw new ApplicationException(CodeType.SERVICE_ERROR, "修改异常");
            }
        }


        //更新用户跟班表的刷新时间
        attendantUserService.updateAttTime(expTime, attUserId);


        Map<String, Object> map = new HashMap<>();
        map.put("expTime",expTime);

        //得到产出的物品返回
        map.put("goods",list);

        return map;
    }

    @Override
    public Map<String, Object> combatResults(AttendantVo vo) {

        Map<String, Object> map = new HashMap<>();

        ResultAttendeantVo result = getResult(vo.getMyHealth(),vo.getOtherHealth(),vo.getMyCapabilities(), vo.getOtherForce(), vo.getStatus());

        map.put("myMuch", result.getMyMuch());
        map.put("otherMuch",result.getOtherMuch());
        //我方打对方一次掉的血量
        map.put("myHealth", vo.getMyCapabilities());
        //对方打我方掉的血量
        map.put("otherHealth", vo.getOtherForce());

        //0--我方赢了
        //1--对面赢了
        map.put("result",result);

        return map;
    }


    public ResultAttendeantVo getResult (Integer allMyHealth, Integer allOtherHealth, Integer myCapabilities, Integer otherForce, Integer status) {

        ResultAttendeantVo vo = new ResultAttendeantVo();

        Integer myMuch = 0;
        Integer otherMuch = 0;

        if (status == 0) {
            //我先动手
            while (true) {
                Integer other = allOtherHealth - myCapabilities;
                //得到被攻击后的血量    在赋值给总血量
                allOtherHealth=other;
                myMuch += 1;
                if (other <= 0) {
                    //我方赢了
                    vo.setMyMuch(myMuch);
                    vo.setOtherMuch(otherMuch);
                    vo.setResult(0);
                    return vo;
                }
                Integer my = allMyHealth - otherForce;
                //得到被攻击后的血量    在赋值给总血量
                allMyHealth=my;
                otherMuch += 1;
                if (my <= 0) {
                    //对方赢了
                    vo.setMyMuch(myMuch);
                    vo.setOtherMuch(otherMuch);
                    vo.setResult(1);
                    return vo;
                }
            }


        }

        //对面先动手
        while (true) {
            Integer my = allMyHealth - otherForce;
            //得到被攻击后的血量    在赋值给总血量
            allMyHealth=my;
            otherMuch += 1;
            if (my <= 0) {
                //对方赢了
                vo.setMyMuch(myMuch);
                vo.setOtherMuch(otherMuch);
                vo.setResult(1);
                return vo;
            }
            Integer other = allOtherHealth - myCapabilities;
            //得到被攻击后的血量    在赋值给总血量
            allOtherHealth=other;
            myMuch += 1;
            if (other <= 0) {
                //我方赢了
                vo.setMyMuch(myMuch);
                vo.setOtherMuch(otherMuch);
                vo.setResult(0);
                return vo;
            }
        }

    }


    @Override
    public Map<String,Object> queryAidUser(Long CaughtPeopleId) {
        //得到用户登录的token信息
        UserLoginQuery query = localUser.getUser();

        Map<String,Object> map=new HashMap<>();
        //主人信息
        AttendantUserVo attendantUserVo = attendantMapper.queryAidUser(CaughtPeopleId);
        if(attendantUserVo == null){
            map.put("msg","没有主人");
        }else{
            map.put("attendantUserVo",attendantUserVo);
        }
        //自己的信息
        Result<UserInfoQueryBo> userInfoQueryBoResult = userFeignClient.queryUser(query.getId());
        if (userInfoQueryBoResult.getCode() != 0) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "你他妈就是个傻逼");
        }
        map.put("UserInfoQueryBo",userInfoQueryBoResult.getData());

        return map;
    }

    @Override
    public List<AttenDant> listAttenDant() {
        return attendantMapper.selectList(null);
    }


}
