package com.dkm.medal.service.impl;

import com.dkm.medal.dao.MedalDao;
import com.dkm.medal.entity.vo.MedalUserInfoVo;
import com.dkm.medal.service.MedalService;
import com.dkm.utils.IdGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: game_project
 * @description: 勋章实现类
 * @author: zhd
 * @create: 2020-06-05 14:56
 **/
@Service
public class MedalServiceImpl implements MedalService {

    @Resource
    private IdGenerator idGenerator;

    @Resource
    private MedalDao medalDao;

    @Override
    public List<MedalUserInfoVo> getUserMedal(Long userId, Integer type) {
        return medalDao.selectUserMedal(userId,type);
    }

    @Override
    public MedalUserInfoVo getOneUserMedal(Long userId, Long medalId) {
        return medalDao.selectOneUserMedal(userId, medalId);
    }
}
