package com.dkm.turntable.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dkm.pets.dao.FoodDetailMapper;
import com.dkm.pets.entity.FoodDetailEntity;
import com.dkm.turntable.dao.TurntableDao;
import com.dkm.turntable.entity.TurntableEntity;
import com.dkm.turntable.entity.vo.TurntableInfoVo;
import com.dkm.turntable.service.ITurntableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: game_project
 * @description: 实现类
 * @author: zhd
 * @create: 2020-06-11 10:05
 **/
@Service
public class TurntableServiceImpl implements ITurntableService {


    @Resource
    private TurntableDao turntableDao;

    @Resource
    private FoodDetailMapper foodDetailMapper;

    @Override
    public List<TurntableInfoVo> getTurntable(Long userId, Integer type) {
        List<FoodDetailEntity> foodDetailEntities = foodDetailMapper.selectList(null);
        List<TurntableInfoVo> result = new ArrayList<>();
        foodDetailEntities.forEach(a->{
            TurntableInfoVo b = new TurntableInfoVo();
            b.setName(a.getFoodName());
            b.setNumber(type);
            b.setUrl(a.getFoodUrl());
            result.add(b);
            if("蜂蜜".equals(a.getFoodName())) {
                TurntableInfoVo c = b;
                c.setNumber(type*3);
                result.add(c);
            }
        });
        result.add(new TurntableInfoVo(1234*type,"https://ae01.alicdn.com/kf/H9af2f2605a3d4402a75e0261cc75812fv.jpg","金币"));
        result.add(new TurntableInfoVo(666*type,"https://ae01.alicdn.com/kf/H1bf1f90e52745c9843fa1d5fdbf339d0.jpg","经验"));
        return result;
    }
}
