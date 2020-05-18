package com.dkm.turntable.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoBo;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.feign.UserFeignClient;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private UserFeignClient feignClient;
    @Autowired
    private LocalUser localUser;
    @Autowired
    private TurntableItemMapper turntableItemMapper;

    @Override
    public List<TurntableItemBO> luckyDrawItems() {
        UserLoginQuery query = localUser.getUser();
        Result<UserInfoQueryBo> result = feignClient.queryUser(query.getId());
        UserInfoQueryBo resultData = result.getData();
        //获取用户等级
        Integer userLevel = resultData.getUserInfoGrade();
        //用户等级除10得到当前转盘处于哪一程度
        Integer userCeil = (int)Math.ceil(userLevel / LEVEL_MUCH_DOUBLE);
        //获取转盘表对应级别的6个物品
        LambdaQueryWrapper<Turntable> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Turntable::getTurntableUserLevel,userCeil);
        Turntable turntable = baseMapper.selectOne(queryWrapper);
        //把6个物品的ID换成该物品的属性内容
        List<TurntableItem> turntableItems = turntableItemMapper.luckyDrawItems(turntable);
        //把物品属性对象转化成Map对象：id-对象
        Map<Long, TurntableItem> turntableItemMap = turntableItems.stream().collect(Collectors.toMap(TurntableItem::getTurntableItemId, turntableItem -> turntableItem));
        ArrayList<TurntableItemBO> turntableItemBoS = new ArrayList<>();
        turntableItemBoS.add(exchangeTurntableItemBO(turntable.getTurntableSlotOneId(),turntableItemMap));
        turntableItemBoS.add(exchangeTurntableItemBO(turntable.getTurntableSlotTwoId(),turntableItemMap));
        turntableItemBoS.add(exchangeTurntableItemBO(turntable.getTurntableSlotThreeId(),turntableItemMap));
        turntableItemBoS.add(exchangeTurntableItemBO(turntable.getTurntableSlotFourId(),turntableItemMap));
        turntableItemBoS.add(exchangeTurntableItemBO(turntable.getTurntableSlotFiveId(),turntableItemMap));
        turntableItemBoS.add(exchangeTurntableItemBO(turntable.getTurntableSlotSixId(),turntableItemMap));
        return turntableItemBoS;
    }
    /**
     * 兑换对象
     * @param itemId 物品ID
     * @param turntableItemMap 包含可能出现的物品ID的对象内容
     * @return 对象
     */
    private TurntableItemBO exchangeTurntableItemBO(Long itemId,Map<Long, TurntableItem> turntableItemMap){
        TurntableItem turntableItem = turntableItemMap.get(itemId);
        TurntableItemBO turntableItemBO = new TurntableItemBO();
        turntableItemBO.setTurntableItemName(turntableItem.getTurntableItemName());
        turntableItemBO.setTurntableItemImageUrl(turntableItem.getTurntableItemImageUrl());
        turntableItemBO.setTurntableItemRare(turntableItem.getTurntableItemRare());
        return turntableItemBO;
    }
}
