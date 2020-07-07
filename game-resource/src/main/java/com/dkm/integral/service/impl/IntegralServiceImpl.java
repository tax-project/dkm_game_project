package com.dkm.integral.service.impl;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.integral.dao.IntegralMapper;
import com.dkm.integral.entity.Integral;
import com.dkm.integral.entity.Stars;
import com.dkm.integral.entity.UserIntegral;
import com.dkm.integral.service.IIntegralService;
import com.dkm.jwt.contain.LocalUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/22 18:43
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class IntegralServiceImpl implements IIntegralService {
    @Autowired
    private IntegralMapper integralMapper;


    @Autowired
    private LocalUser localUser;


    @Override
    public List<Integral> queryAllIntegral() {
        return integralMapper.queryAllIntegral();
    }

    @Override
    public Integer queryUserIdIntegral() {
        Integer integer = integralMapper.queryUserIdIntegral(localUser.getUser().getId());
        if(integer==null){
            //初始化积分
            UserIntegral userIntegral=new UserIntegral();
            userIntegral.setUserId(localUser.getUser().getId());
            userIntegral.setIMyIntegral(0);
            integralMapper.insert(userIntegral);
        }
        Integer queryInteger = integralMapper.queryUserIdIntegral(localUser.getUser().getId());
        return queryInteger;
    }

    @Override
    public int updateUserIntegral(Integer iMyIntegral) {
        System.out.println(localUser.getUser().getId());
        int i = integralMapper.updateUserIntegral(iMyIntegral, localUser.getUser().getId());
        if(i<=0){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"修改用户积分异常");
        }
        return i;
    }

    @Override
    public int updateUserByIntegral(Long userId) {
        int i = integralMapper.updateUserByIntegral(userId);
        if(i<=0){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"修改用户积分异常");
        }
        return i;
    }

    @Override
    public int updateUserStarsNumber(Integer sCurrentlyHasNum,Integer sStar) {
        Stars stars=new Stars();
        stars.setSCurrentlyHasNum(sCurrentlyHasNum);
        stars.setUserId(localUser.getUser().getId());
        stars.setSStar(sStar);
        int i = integralMapper.updateUserStarsNumber(stars);
        if(i<=0){
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"修改用户星星数量异常");
        }
        return i;
    }
}
