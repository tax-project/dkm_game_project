package com.dkm.attendant.service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.attendant.dao.AttendantMapper;
import com.dkm.attendant.entity.AttenDant;
import com.dkm.attendant.entity.vo.User;
import com.dkm.attendant.service.IAttendantService;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVo;
import com.dkm.knapsack.service.ITbEquipmentKnapsackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private ITbEquipmentKnapsackService iTbEquipmentKnapsackService;
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
     * 解雇
     */


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
        return iTbEquipmentKnapsackService.selectUserIdAndFoodId(query.getId());
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

    @Override
    public int dismissal(Long id) {
        return attendantMapper.dismissal(id);
    }

    @Override
    public List<User> queryUserPetBattle() {
        //得到用户登录的token信息
        UserLoginQuery query = localUser.getUser();

        return null;
    }

    @Override
    public int addGraspFollowing() {

        return 0;
    }

}
