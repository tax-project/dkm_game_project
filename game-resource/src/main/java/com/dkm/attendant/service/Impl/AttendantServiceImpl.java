package com.dkm.attendant.service.Impl;


import com.dkm.attendant.dao.AttendantMapper;
import com.dkm.attendant.entity.AttenDant;
import com.dkm.attendant.entity.AttendantUser;
import com.dkm.attendant.entity.bo.AttUserResultBo;
import com.dkm.attendant.entity.vo.*;
import com.dkm.attendant.service.IAttendantService;
import com.dkm.attendant.service.IAttendantUserService;
import com.dkm.config.RedisConfig;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.entity.vo.AttendantWithUserVo;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.BaseFeignClient;
import com.dkm.feign.UserFeignClient;
import com.dkm.feign.entity.PetsDto;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVo;
import com.dkm.knapsack.service.ITbEquipmentKnapsackService;
import com.dkm.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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

    private final String REDIS_LOCK = "REDIS::LOCK:ATTENDANT";

    /**
     * 获取用户抓到的跟班信息
     * @return
     */
    @Override
    public Map<String,Object> queryThreeAtt() {
        //得到用户登录的token信息
        UserLoginQuery query = localUser.getUser();
        //查询到所有系统跟班
        List<AttUserAllInfoVo> list = attendantMapper.queryThreeAtt(query.getId(), 0);

        //查询到所有用户跟班
        List<AttUserAllInfoVo> list1 = attendantMapper.queryThreeAtt(query.getId(), 1);

        Map<String,Object> map = new HashMap<>(2);

        //系统跟班
        map.put("sys-att",list);

        //用户跟班
        map.put("user-att",list1);

        return map;
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
            return bo;
        }).collect(Collectors.toList());

        //得到系统跟班列表
        List<AttenDant> list = attendantMapper.selectList(null);

        Map<String, Object> map = new HashMap<>(2);
        map.put("sys",list);
        map.put("userInfo",attUserResultBoList);

        return map;
    }
    /**
     * 解雇
     */
    @Override
    public int dismissal(Long caughtPeopleId) {
        return attendantMapper.dismissal(caughtPeopleId);
    }

    @Override
    public Map<String, Object> petBattle(Long caughtPeopleId) {
        //我方随机上场的宠物
        String myPet=null;

        //他方随机上场的宠物
        String hePet=null;

        //我方装备血量之和
        double edLisfe=0;

        //他方装备血量之和
        double heEdLisfe=0;

        //我方各装被属性加成
        double bonuses=0;

        //他各装被属性加成
        double heBonuses=0;

        //我方装备加成
        double myEquipBonus=0;

        //她方装备加成
        double heEquipBonus=0;

        //我方防御力
        double ourDefenses=0;
        //他方防御力
        double defenseOtherSide=0;

        //得到他方血量
        double ourHealth1=0;

        //得到我方血量
        double ourHealth=0;

        //得到我方装备防御力
        double ourEquipmentDefense=0;

        //得到他方装备防御力
        double capabilities=0;
        //得到我方战力
        Integer ourCapabilities=0;

        //得到他方的战力
        Integer otherForce=0;

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


        Integer defaultOtherForce=0;
        //得到装备信息
        List<TbEquipmentKnapsackVo> tbEquipmentKnapsackVos1 = iTbEquipmentKnapsackService.selectUserIdTwo(caughtPeopleId);
        //如果没有装备
        if(null == tbEquipmentKnapsackVos1 && tbEquipmentKnapsackVos1.size()==0){
            //血量
            ourHealth1=500;
            //他方装备防御力
            capabilities=0;
            //得到他方的战力
            otherForce=100;
            System.out.println("对方没有装备 = " + "对方没有装备");
        }else{
            System.out.println("对方战斗人装备数据 = " + tbEquipmentKnapsackVos1);
            for (int i = 0; i < tbEquipmentKnapsackVos1.size(); i++) {
                //装备血量之和
                BigDecimal edLife = tbEquipmentKnapsackVos1.get(i).getEdLife();
                heEdLisfe=heEdLisfe+edLife.intValue();

                if(tbEquipmentKnapsackVos1.get(i).getEdAttribute().intValue()==1 && tbEquipmentKnapsackVos1.get(i).getEdType().intValue()==1){
                        System.out.println("tbEquipmentKnapsackVos1.get(i).getEdTypevalue().intValue() = " + tbEquipmentKnapsackVos1.get(i).getEdTypeonevalue().intValue());
                        //他方装备加成
                        heEquipBonus=tbEquipmentKnapsackVos1.get(i).getEdTypeonevalue().doubleValue();
                    }else if(tbEquipmentKnapsackVos1.get(i).getEdType().intValue()==1){
                        //他方装备加成
                        heEquipBonus=tbEquipmentKnapsackVos1.get(i).getEdTypeonevalue().doubleValue();
                    }


                //判断有些装备没有才华  默认赋值为0
                if(tbEquipmentKnapsackVos1.get(i).getEdDefense()==null ||tbEquipmentKnapsackVos1.get(i).getEdDefense().intValue()==0){
                    defenseOtherSide=defenseOtherSide;
                }
                //得到他方防御力
                defenseOtherSide=defenseOtherSide+tbEquipmentKnapsackVos1.get(i).getEdDefense().intValue();


                        System.out.println("哈哈哈哈 = " + 123);
                        heBonuses=heBonuses+tbEquipmentKnapsackVos1.get(i).getEdEquipmentReputation().doubleValue()+tbEquipmentKnapsackVos1.get(i).getEdRedEnvelopeAcceleration().doubleValue()
                                +tbEquipmentKnapsackVos1.get(i).getEdDefense().doubleValue()+tbEquipmentKnapsackVos1.get(i).getEdAttack().doubleValue();
                    }

            //如果等于0 说明这个装备没有属性加成
            if(heBonuses==0){
                //得到他方血量
                ourHealth1=heEdLisfe;
            }else{
                //得到他方血量
                ourHealth1=heEdLisfe*heBonuses;
            }

            System.out.println("得到他方血量 = " + ourHealth1);
            //得到他方装备防御力
            capabilities = defenseOtherSide * heBonuses;

            if(myEquipBonus==0){
                //我方装备加成
                List<TbEquipmentKnapsackVo> tbEquipmentKnapsackVos = iTbEquipmentKnapsackService.selectUserIdTwo(query.getId());
                for (int i = 0; i < tbEquipmentKnapsackVos.size(); i++) {
                    int status = tbEquipmentKnapsackVos.get(i).getEdAttribute().intValue();
                    if(status == 1 && tbEquipmentKnapsackVos.get(i).getEdType().intValue()==1){
                        //生命值
                        //我方装备加成
                        myEquipBonus=tbEquipmentKnapsackVos.get(i).getEdTypeonevalue().doubleValue();
                        System.out.println("myEquipBonus 装备加成 生命= " + myEquipBonus);
                    }else if(tbEquipmentKnapsackVos.get(i).getEdType().intValue()==2){
                        System.out.println("myEquipBonus 装备加成 才华= " + myEquipBonus);
                        //我方装备加成
                        myEquipBonus=tbEquipmentKnapsackVos.get(i).getEdTypeonevalue().doubleValue();
                    }
                }

            }
            //得到我方战力
            double ripetime = Math.pow(userInfoQueryBoResult.getData().getUserInfoRenown(), 1/ 2.0)
                    +(userInfoQueryBoResult.getData().getUserInfoRenown()*myEquipBonus-userInfoQueryBoResultCaughtPeopleId.getData().getUserInfoRenown()+heEquipBonus);

            System.out.println(userInfoQueryBoResult.getData().getUserInfoRenown() * myEquipBonus - userInfoQueryBoResultCaughtPeopleId.getData().getUserInfoRenown() + heEquipBonus);
            System.out.println("我方属性加成 = " + myEquipBonus);
            System.out.println("他方属性加成 = " + heEquipBonus);
            System.out.println("我方声望 = " + userInfoQueryBoResult.getData().getUserInfoRenown());
            System.out.println("他方声望 = " + userInfoQueryBoResultCaughtPeopleId.getData().getUserInfoRenown());
            System.out.println("ripetime = " + ripetime);
            System.out.println("userInfoQueryBoResult.getData().getUserInfoRenown()*myEquipBonus = " + userInfoQueryBoResult.getData().getUserInfoRenown()*myEquipBonus);
            System.out.println("userInfoQueryBoResultCaughtPeopleId.getData().getUserInfoRenown()+heEquipBonus = " + userInfoQueryBoResultCaughtPeopleId.getData().getUserInfoRenown() + heEquipBonus);
            //得到我方战力
            ourCapabilities = Integer.valueOf((int) ripetime);
            System.out.println("我方战力 = " + ourCapabilities);

            //得到他方的战力
            double heRipetime = Math.pow(userInfoQueryBoResultCaughtPeopleId.getData().getUserInfoRenown(), 1 / 2.0)
                    +(userInfoQueryBoResultCaughtPeopleId.getData().getUserInfoRenown()*heEquipBonus-userInfoQueryBoResult.getData().getUserInfoRenown()+myEquipBonus);
            System.out.println("heEquipBonus11111 = " + heEquipBonus);
            System.out.println("myEquipBonus11111 = " + myEquipBonus);
            //得到他方的战力
            otherForce = Integer.valueOf((int) heRipetime);
            System.out.println("他方战力 = " + otherForce);
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
            ourEquipmentDefense=0;
            //得到我方战力
            ourCapabilities=100;
            System.out.println("没有装备 = " + "没有装备");
        }else{
            if(tbEquipmentKnapsackVos.size()==0){
                //得到我方战力
                ourCapabilities=100;
            }
            for (int i = 0; i < tbEquipmentKnapsackVos.size(); i++) {
                //装备血量之和
                BigDecimal edLife = tbEquipmentKnapsackVos.get(i).getEdLife();
                edLisfe=edLisfe+edLife.intValue();

                    if(tbEquipmentKnapsackVos.get(i).getEdAttribute().intValue()==1){
                        if(tbEquipmentKnapsackVos.get(i).getEdType().intValue()==1){
                            //等等于1是生命的值
                            //我方装备加成
                            myEquipBonus=tbEquipmentKnapsackVos.get(i).getEdTypeonevalue().doubleValue();
                            //等等于2是才华的值
                        }else if(tbEquipmentKnapsackVos.get(i).getEdType().intValue()==2){
                            //我方装备加成
                            myEquipBonus=tbEquipmentKnapsackVos.get(i).getEdTypeonevalue().doubleValue();
                        }
                    }

                //判断有些装备没有才华将自己赋值给自己
                if(tbEquipmentKnapsackVos.get(i).getEdDefense()==null ||tbEquipmentKnapsackVos.get(i).getEdDefense().intValue()==0){
                    ourDefenses=ourDefenses;
                }
                //得到用户防御力
                ourDefenses=ourDefenses+tbEquipmentKnapsackVos.get(i).getEdDefense().intValue();

                        // 生命值或才华
                        //各个装备属性加成
                        bonuses=bonuses+tbEquipmentKnapsackVos.get(i).getEdEquipmentReputation().doubleValue()+tbEquipmentKnapsackVos.get(i).getEdRedEnvelopeAcceleration().doubleValue()
                                +tbEquipmentKnapsackVos.get(i).getEdDefense().doubleValue()+tbEquipmentKnapsackVos.get(i).getEdAttack().doubleValue();

            }
            System.out.println("我方战斗力 = " + ourCapabilities);
            //得到我方血量
            ourHealth=edLisfe*bonuses;
            System.out.println("得到我方血量 = " + ourHealth);
            //得到我方装备防御力
            ourEquipmentDefense = ourDefenses * bonuses;
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
        //我方血量
        map.put("ourHealth",ourHealth+ourEquipmentDefense);
        //他方血量
        map.put("ourHealth1",ourHealth1+capabilities);
        //我方战力
        map.put("ourCapabilities",ourCapabilities);
        System.out.println("他方战斗力 = " + otherForce);
        //他方战力
        map.put("otherForce",otherForce);
        return map;
    }


    @Override
    public AttUserVo addGraspFollowing(Long caughtPeopleId, Integer status, Long attendantId) {

        UserLoginQuery user = localUser.getUser();

        AttUserVo vo = new AttUserVo();

        //根据用户Id查询所有跟班信息
        List<AttendantUser> list = attendantUserService.queryListByUserId(user.getId());

        if (list.size() >= 6) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "您最多只能抓6个跟班");
        }

        //推后12小时
        Long s=System.currentTimeMillis()/1000+43200;
        vo.setS(s);
        AttendantUser attendantUser=new AttendantUser();

        if (status == 1) {
            //抓用户跟班

            try {
                //加锁,保证原子性
                Boolean lock = redisConfig.redisLock(REDIS_LOCK);

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
                    attendantUser.setExp1(s);
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
                attendantUser.setExp1(s);
                attendantUserService.insert(attendantUser);
                //代表抢用户跟班成功
                vo.setStatus(0);
                return vo;
            } finally {
                redisConfig.deleteLock(REDIS_LOCK);
            }

        }

        //抓系统跟班
        attendantUser.setAtuId(idGenerator.getNumberId());
        attendantUser.setAttendantId(attendantId);
        attendantUser.setCaughtPeopleId(0L);
        attendantUser.setUserId(user.getId());
        attendantUser.setExp1(s);
        attendantUserService.insert(attendantUser);
        vo.setStatus(0);
        return vo;
    }





    @Override
    public int gather(Integer atuId) {
        long exp1 = System.currentTimeMillis() / 1000 + 43200;
        int gather = attendantMapper.gather(exp1,Long.valueOf(atuId));
        return gather;
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
        System.out.println("1-:"  +attendantUserVo);
        if(attendantUserVo==null){
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
