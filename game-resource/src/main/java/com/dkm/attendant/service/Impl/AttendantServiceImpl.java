package com.dkm.attendant.service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.attendant.dao.AttendantMapper;
import com.dkm.attendant.entity.AttenDant;
import com.dkm.attendant.entity.AttendantUser;
import com.dkm.attendant.entity.vo.User;
import com.dkm.attendant.service.IAttendantService;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.UserFeignClient;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVo;
import com.dkm.knapsack.service.ITbEquipmentKnapsackService;
import com.dkm.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String,Object> map=new HashMap<>();
        //得到用户登录的token信息
        UserLoginQuery query = localUser.getUser();
        //自己的信息
        Result<UserInfoQueryBo> userInfoQueryBoResult = userFeignClient.queryUser(query.getId());
        //对手用户信息
        Result<UserInfoQueryBo> userInfoQueryBoResultCaughtPeopleId = userFeignClient.queryUser(caughtPeopleId);


        map.put("userInfoQueryBoResult",userInfoQueryBoResult);
        map.put("userInfoQueryBoResultCaughtPeopleId",userInfoQueryBoResultCaughtPeopleId);

        return null;
    }


    @Override
    public Long addGraspFollowing(Long caughtPeopleId) {
        Long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        Long s=second+43200;
        AttendantUser attendantUser=new AttendantUser();
        long a=(long) (Math.random()*(3))+1;
        attendantUser.setAtuId(idGenerator.getNumberId());
        attendantUser.setAId(a);
        attendantUser.setCaughtPeopleId(caughtPeopleId);
        attendantUser.setUserId(localUser.getUser().getId());
        attendantUser.setExp1(System.currentTimeMillis()/1000+43200);
        attendantMapper.addGraspFollowing(attendantUser);
        return s;
    }

    @Override
    public int gather(Integer autId) {
        long exp1 = System.currentTimeMillis() / 1000 + 43200;
        int gather = attendantMapper.gather(exp1,autId);
        return gather;
    }

}
