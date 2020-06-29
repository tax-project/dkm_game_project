package com.dkm.turntable.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dkm.turntable.dao.GoodsMapper;
import com.dkm.turntable.dao.TurntableDao;
import com.dkm.turntable.entity.GoodsEntity;
import com.dkm.turntable.entity.vo.TurntableInfoVo;
import com.dkm.turntable.service.ITurntableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    private GoodsMapper goodsMapper;

    @Override
    public List<TurntableInfoVo> getTurntable(Long userId, Integer type) {
        List<GoodsEntity> foodDetailEntities = goodsMapper.selectList(new LambdaQueryWrapper<GoodsEntity>().eq(GoodsEntity::getGoodType,3));
        List<TurntableInfoVo> result = new ArrayList<>();
        foodDetailEntities.forEach(a->{
            TurntableInfoVo b = new TurntableInfoVo();
            b.setName(a.getName());
            b.setNumber(type);
            b.setUrl(a.getUrl());
            result.add(b);
            if("蜂蜜".equals(a.getName())) {
                TurntableInfoVo c = b;
                c.setNumber(type*3);
                result.add(c);
            }
        });
        result.add(new TurntableInfoVo(1234*type,"https://ae01.alicdn.com/kf/H9af2f2605a3d4402a75e0261cc75812fv.jpg","金币",0L));
        result.add(new TurntableInfoVo(666*type,"https://ae01.alicdn.com/kf/H1bf1f90e52745c9843fa1d5fdbf339d0.jpg","经验",0L));
        return result;
    }
}
