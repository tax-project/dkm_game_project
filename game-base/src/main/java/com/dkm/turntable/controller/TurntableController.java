package com.dkm.turntable.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dkm.turntable.dao.TurntableItemMapper;
import com.dkm.turntable.dao.TurntableMapper;
import com.dkm.turntable.entity.Turntable;
import com.dkm.turntable.entity.TurntableItem;
import com.dkm.turntable.entity.bo.TurntableItemBO;
import com.dkm.turntable.service.ITurntableService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: HuangJie
 * @Date: 2020/5/9 14:42
 * @Version: 1.0V
 */
@Api(tags = "幸运大转盘API")
@RestController
@RequestMapping("v1/turntable")
public class TurntableController {

    @Autowired
    private ITurntableService turntableService;
    @Autowired
    private TurntableMapper turntableMapper;
    @Autowired
    private TurntableItemMapper turntableItemMapper;

    @GetMapping("/lucky/draw/items")
    public List<TurntableItemBO> luckyDrawItems(){
        return turntableService.luckyDrawItems();
    }

    @GetMapping("/test")
    public void  test(){
        LambdaQueryWrapper<Turntable> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Turntable::getTurntableUserLevel,1);
        Turntable turntable = turntableMapper.selectOne(queryWrapper);
        List<TurntableItem> turntableItems = turntableItemMapper.luckyDrawItems(turntable);
        System.out.println(turntableItems.stream().map(turntableItem -> {
            TurntableItemBO turntableItemBO = new TurntableItemBO();
            turntableItemBO.setTurntableItemName(turntableItem.getTurntableItemName());
            turntableItemBO.setTurntableItemImageUrl(turntableItem.getTurntableItemImageUrl());
            turntableItemBO.setTurntableItemRare(turntableItem.getTurntableItemRare());
            return turntableItemBO;
        }).collect(Collectors.toList()));
    }

}
