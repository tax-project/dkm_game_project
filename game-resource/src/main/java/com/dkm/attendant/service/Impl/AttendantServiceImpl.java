package com.dkm.attendant.service.Impl;


import com.dkm.attendant.dao.AttendantMapper;
import com.dkm.attendant.entity.AttenDant;
import com.dkm.attendant.entity.AttendantUser;
import com.dkm.attendant.entity.vo.AttendantVo;
import com.dkm.attendant.entity.vo.ResultAttendeantVo;
import com.dkm.attendant.entity.vo.User;
import com.dkm.attendant.service.IAttendantService;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.BaseFeignClient;
import com.dkm.feign.UserFeignClient;
import com.dkm.feign.entity.PetsDto;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVo;
import com.dkm.knapsack.service.ITbEquipmentKnapsackService;
import com.dkm.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    /**
     * 获取用户抓到的跟班信息
     * @return
     */
    @Override
    public List<AttenDant> queryThreeAtt() {
        //得到用户登录的token信息
        UserLoginQuery query = localUser.getUser();
        return attendantMapper.queryThreeAtt(query.getId());
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
    public List<User> queryRandomUser() {
        //得到用户登录的token信息
        UserLoginQuery query = localUser.getUser();
        List<User> users = attendantMapper.queryRandomUser();
        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getUserId()==query.getId()){
                users.remove(i);
            }
        }
        return users;
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
        Integer edLisfe=0;

        //他方装备血量之和
        Integer heEdLisfe=0;

        //我方各装被属性加成
        Integer bonuses=0;

        //他各装被属性加成
        Integer heBonuses=0;

        //我方装备加成
        Integer myEquipBonus=0;

        //她方装备加成
        Integer heEquipBonus=0;

        //我方防御力
        Integer ourDefenses=0;
        //他方防御力
        Integer defenseOtherSide=0;


        Map<String,Object> map=new HashMap<>();
        //得到用户登录的token信息
        UserLoginQuery query = localUser.getUser();
        //自己的信息
        Result<UserInfoQueryBo> userInfoQueryBoResult = userFeignClient.queryUser(query.getId());
        //对手用户信息
        Result<UserInfoQueryBo> userInfoQueryBoResultCaughtPeopleId = userFeignClient.queryUser(caughtPeopleId);



        //我方宠物信息
        Result<List<PetsDto>> petInfo = baseFeignClient.getPetInfo(query.getId());
        System.out.println("petInfo = " + petInfo.getData().size());
        //随机获取我方宠物
        PetsDto myPetsDto = petInfo.getData().get(new Random().nextInt(petInfo.getData().size()));
        myPet=myPetsDto.getPetName();

        //得到装备信息
        List<TbEquipmentKnapsackVo> tbEquipmentKnapsackVos = iTbEquipmentKnapsackService.selectUserIdTwo(query.getId());
        for (int i = 0; i < tbEquipmentKnapsackVos.size(); i++) {
            //装备血量之和
            BigDecimal edLife = tbEquipmentKnapsackVos.get(i).getEdLife();
            edLisfe=edLisfe+edLife.intValue();
            //我方装备加成
            myEquipBonus=myEquipBonus+tbEquipmentKnapsackVos.get(i).getEdRedEnvelopeAcceleration().intValue()+
                    tbEquipmentKnapsackVos.get(i).getEdLife().intValue()+tbEquipmentKnapsackVos.get(i).getEdDefense().intValue();
            //判断有些装备没有才华将自己赋值给自己
            if(tbEquipmentKnapsackVos.get(i).getEdDefense()==null ||tbEquipmentKnapsackVos.get(i).getEdDefense().intValue()==0){
                ourDefenses=ourDefenses;
            }
            //得到用户防御力
            ourDefenses=ourDefenses+tbEquipmentKnapsackVos.get(i).getEdDefense().intValue();


            if(tbEquipmentKnapsackVos.get(i).getEdAttribute().intValue()==1){
                if(tbEquipmentKnapsackVos.get(i).getEdType().intValue()==1){
                   // 生命值
                    bonuses=bonuses+tbEquipmentKnapsackVos.get(i).getEdTypevalue().intValue();
                }
            }
        }


        //他方宠物信息
        Result<List<PetsDto>> petInfo1 = baseFeignClient.getPetInfo(caughtPeopleId);
        //随机获取他方宠物
        PetsDto hePetsDto = petInfo1.getData().get(new Random().nextInt(petInfo1.getData().size()));
        hePet=hePetsDto.getPetName();

        //得到装备信息
        List<TbEquipmentKnapsackVo> tbEquipmentKnapsackVos1 = iTbEquipmentKnapsackService.selectUserIdTwo(caughtPeopleId);
        for (int i = 0; i < tbEquipmentKnapsackVos1.size(); i++) {
            //装备血量之和
            BigDecimal edLife = tbEquipmentKnapsackVos1.get(i).getEdLife();
            heEdLisfe=heEdLisfe+edLife.intValue();
            //他方装备加成
            heEquipBonus=heEquipBonus+tbEquipmentKnapsackVos1.get(i).getEdRedEnvelopeAcceleration().intValue()+
                    tbEquipmentKnapsackVos1.get(i).getEdLife().intValue()+tbEquipmentKnapsackVos1.get(i).getEdDefense().intValue();

            //判断有些装备没有才华  默认赋值为0
            if(tbEquipmentKnapsackVos1.get(i).getEdDefense()==null ||tbEquipmentKnapsackVos1.get(i).getEdDefense().intValue()==0){
                defenseOtherSide=defenseOtherSide;
            }
            //得到他方防御力
            defenseOtherSide=defenseOtherSide+tbEquipmentKnapsackVos1.get(i).getEdDefense().intValue();

            if(tbEquipmentKnapsackVos1.get(i).getEdAttribute().intValue()==1){
                if(tbEquipmentKnapsackVos1.get(i).getEdType().intValue()==1){
                    heBonuses=heBonuses+tbEquipmentKnapsackVos1.get(i).getEdTypevalue().intValue();

                }
            }
        }
        //得到我方血量
        Integer ourHealth=edLisfe*bonuses;

        //得到他方血量
        Integer ourHealth1=heEdLisfe*heBonuses;

        //得到我方装备防御力
        int ourEquipmentDefense = ourDefenses * bonuses;

        //得到他方装备防御力
        int capabilities = defenseOtherSide * heBonuses;

        //得到我方战力
        double ripetime = Math.pow(userInfoQueryBoResult.getData().getUserInfoRenown(), 1 / 2.0)
                +(userInfoQueryBoResult.getData().getUserInfoRenown()*myEquipBonus-userInfoQueryBoResultCaughtPeopleId.getData().getUserInfoRenown()+heEquipBonus);

        Integer ourCapabilities = Integer.valueOf((int) ripetime);

        //得到他方的战力
        double heRipetime = Math.pow(userInfoQueryBoResultCaughtPeopleId.getData().getUserInfoRenown(), 1 / 2.0)
                +(userInfoQueryBoResultCaughtPeopleId.getData().getUserInfoRenown()*heEquipBonus-userInfoQueryBoResult.getData().getUserInfoRenown()+myEquipBonus);

        Integer otherForce = Integer.valueOf((int) heRipetime);
        //如果双方宠物相同 等级高的先动手
        if(myPet.equals(hePet)){
           if(myPetsDto.getPGrade().equals(hePetsDto.getPGrade())){
            if(myPetsDto.getPGrade()>hePetsDto.getPGrade()){
                //我方先动手
                map.put("status",0);
            }else{
                //他方先动手
                map.put("status",1);
            }
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
        //他方战力
        map.put("otherForce",otherForce);
        return map;
    }


    @Override
    public Long addGraspFollowing(Long caughtPeopleId) {
        Long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        Long s=second+43200;
        AttendantUser attendantUser=new AttendantUser();
        List<AttenDant> attenDants = attendantMapper.selectList(null);
        //随机获取跟班
        int index1 = (int) (Math.random() * attenDants.size());
        AttenDant attenDant = attenDants.get(index1);
        attendantUser.setAtuId(idGenerator.getNumberId());
        attendantUser.setAId(attenDant.getId());
        attendantUser.setCaughtPeopleId(caughtPeopleId);
        attendantUser.setUserId(localUser.getUser().getId());
        attendantUser.setExp1(System.currentTimeMillis()/1000+43200);
        attendantMapper.addGraspFollowing(attendantUser);
        return s;
    }





    @Override
    public int gather(Integer autId) {
        long exp1 = System.currentTimeMillis() / 1000 + 43200;
        int gather = attendantMapper.gather(exp1,Long.valueOf(autId));
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

}
