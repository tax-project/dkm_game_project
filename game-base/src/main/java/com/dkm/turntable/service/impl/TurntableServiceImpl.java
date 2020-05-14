package com.dkm.turntable.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.turntable.dao.TurntableItemMapper;
import com.dkm.turntable.dao.TurntableMapper;
import com.dkm.turntable.entity.Turntable;
import com.dkm.turntable.entity.TurntableItem;
import com.dkm.turntable.entity.bo.TurntableItemBO;
import com.dkm.turntable.service.ITurntableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: HuangJie
 * @Date: 2020/5/9 16:12
 * @Version: 1.0V
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TurntableServiceImpl extends ServiceImpl<TurntableMapper,Turntable> implements ITurntableService {

    private static final Double LEVEL_MUCH_DOUBLE = 10.0D;
    private static final Integer LEVEL_MUCH_INTEGER = 10;

    @Autowired
    private LocalUser localUser;
    @Autowired
    private TurntableItemMapper turntableItemMapper;

    @Override
    public List<TurntableItemBO> luckyDrawItems() {
        UserLoginQuery userLoginQuery = localUser.getUser();
        assert userLoginQuery!=null;
        //获取用户等级
        Integer userLevel = userLoginQuery.getUserLevel();
        //用户等级除10得到当前转盘处于哪一程度
        Integer userCeil = (int)Math.ceil(userLevel / LEVEL_MUCH_DOUBLE);
        //获取转盘表对应级别的6个物品
        LambdaQueryWrapper<Turntable> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Turntable::getTurntableUserLevel,userCeil);
        Turntable turntable = baseMapper.selectOne(queryWrapper);


        List<TurntableItem> turntableItems = turntableItemMapper.luckyDrawItems(turntable);


        return turntableItems.stream().map(turntableItem -> {
            TurntableItemBO turntableItemBO = new TurntableItemBO();
            turntableItemBO.setTurntableItemName(turntableItem.getTurntableItemName());
            turntableItemBO.setTurntableItemImageUrl(turntableItem.getTurntableItemImageUrl());
            turntableItemBO.setTurntableItemRare(turntableItem.getTurntableItemRare());
            return turntableItemBO;
        }).collect(Collectors.toList());
    }
}
