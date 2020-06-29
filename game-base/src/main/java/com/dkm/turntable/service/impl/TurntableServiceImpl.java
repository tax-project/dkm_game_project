package com.dkm.turntable.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.ResourceFeignClient;
import com.dkm.feign.entity.TbEquipmentKnapsack;
import com.dkm.turntable.dao.GoodsMapper;
import com.dkm.turntable.dao.TurntableDao;
import com.dkm.turntable.entity.GoodsEntity;
import com.dkm.turntable.entity.vo.AddGoodsInfoVo;
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
    private ResourceFeignClient resourceFeignClient;

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public List<TurntableInfoVo> getTurntable(Long userId, Integer type) {
        List<GoodsEntity> goodsEntities = goodsMapper.selectList(null);
        List<TurntableInfoVo> list = new ArrayList<>();
        goodsEntities.forEach(a->{
            list.add(new TurntableInfoVo(type,a.getUrl(),a.getName(),a.getId()));
        });
        return list;
    }

    @Override
    public void addGoods(Long userId, AddGoodsInfoVo addGoodsInfoVo) {
        TbEquipmentKnapsack tbEquipmentKnapsack = new TbEquipmentKnapsack();
        tbEquipmentKnapsack.setFoodId(addGoodsInfoVo.getId());
        tbEquipmentKnapsack.setFoodNumber(addGoodsInfoVo.getNumber());
        tbEquipmentKnapsack.setUserId(userId);
        Result result = resourceFeignClient.addTbEquipmentKnapsackThree(tbEquipmentKnapsack);
        if(result.getCode()!=0)throw  new ApplicationException(CodeType.FEIGN_CONNECT_ERROR,result.getMsg());
    }
}
