package com.dkm.attendant.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.attendant.entity.AttenDant;
import com.dkm.attendant.entity.vo.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: dkm_game
 * @DESCRIPTION:
 * @USER: 刘梦祺
 * @DATE: 2020/5/11 14:05
 */
@Component
public interface AttendantMapper extends BaseMapper<AttenDant> {

    /**
     * 获取用户声望和金币
     * @return
     */
    User queryUserReputationGold(long userId);
}
