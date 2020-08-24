package com.dkm.attendant.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.attendant.dao.AttendantUserMapper;
import com.dkm.attendant.entity.AttendantUser;
import com.dkm.attendant.entity.bo.AttUserInfoBo;
import com.dkm.attendant.service.IAttendantUserService;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.UserFeignClient;
import com.dkm.produce.entity.bo.ProduceBO;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/4 17:52
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AttendantUserServiceImpl extends ServiceImpl<AttendantUserMapper, AttendantUser> implements IAttendantUserService {

    @Autowired
    private UserFeignClient userFeignClient;

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

    @Override
    public AttendantUser queryAttendantUser(Long caughtPeopleId, Long attendantId) {
        LambdaQueryWrapper<AttendantUser> wrapper = new LambdaQueryWrapper<AttendantUser>()
              .eq(AttendantUser::getAttendantId,attendantId)
              .eq(AttendantUser::getUserId,caughtPeopleId);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public void updateAttTime(String endDate, Long id) {
        AttendantUser attendantUser = new AttendantUser();

        attendantUser.setAtuId(id);
        attendantUser.setEndDate(endDate);
        attendantUser.setAttMuch(0);

        int updateById = baseMapper.updateById(attendantUser);

        if (updateById <= 0) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "更新失败");
        }
    }

    @Override
    public AttendantUser queryAttUser(Long id) {
        return baseMapper.selectById(id);
    }


    @Override
    public AttUserInfoBo queryAttUserInfo(Long userId) {
        LambdaQueryWrapper<AttendantUser> wrapper = new LambdaQueryWrapper<AttendantUser>()
              .eq(AttendantUser::getCaughtPeopleId, userId);

        AttendantUser attendantUser = baseMapper.selectOne(wrapper);

        AttUserInfoBo result = new AttUserInfoBo();

        if (attendantUser == null) {
            //没有主人
            result.setStatus(0);
            return result;
        }

        //根据他主人的用户id查询用户信息
        Result<UserInfoQueryBo> userInfoQueryBoResult = userFeignClient.queryUser(attendantUser.getUserId());

        if (userInfoQueryBoResult.getCode() != 0) {
            log.info("user <queryUser> feign err.");
            throw new ApplicationException(CodeType.SERVICE_ERROR);
        }

        UserInfoQueryBo data = userInfoQueryBoResult.getData();

        BeanUtils.copyProperties(data, result);

        result.setStatus(1);

        return result;
    }

    @Override
    public void updateStatus(List<ProduceBO> list) {
        Integer integer = baseMapper.updateStatus(list);

        if (integer <= 0) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "修改失败");
        }
    }

    @Override
    public void updateProduce(List<ProduceBO> list) {

        for (ProduceBO bo : list) {
            AttendantUser attendantUser = new AttendantUser();

            attendantUser.setAttMuch(bo.getMuch());
            attendantUser.setAtuId(bo.getAttId());

            LambdaQueryWrapper<AttendantUser> wrapper = new LambdaQueryWrapper<AttendantUser>()
                  .eq(AttendantUser::getAtuId, bo.getAttId());

            int updateById = baseMapper.update(attendantUser, wrapper);

            if (updateById <= 0) {
                throw new ApplicationException(CodeType.SERVICE_ERROR, "修改失败");
            }
        }
    }
}
