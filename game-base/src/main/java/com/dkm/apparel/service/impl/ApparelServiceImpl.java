package com.dkm.apparel.service.impl;

import com.dkm.apparel.dao.ApparelMapper;
import com.dkm.apparel.entity.ApparelEntity;
import com.dkm.apparel.service.ApparelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: game_project
 * @description: 服饰
 * @author: zhd
 * @create: 2020-05-19 19:22
 **/
@Service
public class ApparelServiceImpl implements ApparelService {
    @Resource
    private ApparelMapper apparelMapper;

    @Override
    public List<ApparelEntity> getAllApparels() {
        return apparelMapper.selectList(null);
    }

    @Override
    public List<ApparelEntity> getUserApparel(Long userId) {
        return apparelMapper.getUserApparel(userId);
    }
}
