package com.dkm.attendant.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.attendant.dao.AttendantUserMapper;
import com.dkm.attendant.entity.AttendantUser;
import com.dkm.attendant.service.IAttendantUserService;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.utils.IdGenerator;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/4 17:52
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AttendantUserServiceImpl extends ServiceImpl<AttendantUserMapper, AttendantUser> implements IAttendantUserService {


    @Override
    public void insert(AttendantUser attendantUser) {
        int insert = baseMapper.insert(attendantUser);

        if (insert <= 0) {
            throw new ApplicationException (CodeType.SERVICE_ERROR);
        }
    }

    @Override
    public AttendantUser queryOne(Long caughtPeopleId) {
        LambdaQueryWrapper<AttendantUser> wrapper = new LambdaQueryWrapper<AttendantUser>()
                .eq(AttendantUser::getCaughtPeopleId,caughtPeopleId);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public List<AttendantUser> queryListByUserId(Long userId) {
        LambdaQueryWrapper<AttendantUser> wrapper = new LambdaQueryWrapper<AttendantUser>()
              .eq(AttendantUser::getUserId, userId);
        return baseMapper.selectList(wrapper);
    }
}
