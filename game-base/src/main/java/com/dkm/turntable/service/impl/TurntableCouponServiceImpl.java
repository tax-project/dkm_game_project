package com.dkm.turntable.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dkm.turntable.dao.TurntableCouponDao;
import com.dkm.turntable.entity.TurntableCouponEntity;
import com.dkm.turntable.service.ITurntableCouponService;
import com.dkm.utils.IdGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: game_project
 * @description: 转盘券
 * @author: zhd
 * @create: 2020-06-11 15:01
 **/
@Service
public class TurntableCouponServiceImpl implements ITurntableCouponService {

    @Resource
    private TurntableCouponDao turntableCouponDao;

    @Resource
    private IdGenerator idGenerator;

    @Override
    public Map<String,Object> getUserCoupon(Long userId) {
        TurntableCouponEntity turntableCouponEntity = turntableCouponDao.selectOne(new LambdaQueryWrapper<TurntableCouponEntity>().eq(TurntableCouponEntity::getUserId, userId));
        LocalDateTime now = LocalDateTime.now();
        Map<String,Object> map = new HashMap<>();
        if(turntableCouponEntity==null){
            //首次登陆
            turntableCouponEntity = new TurntableCouponEntity();
            turntableCouponEntity.setCouponBlue(0);
            turntableCouponEntity.setCouponPurple(0);
            turntableCouponEntity.setCouponGreen(20);
            turntableCouponEntity.setTurntableCouponId(idGenerator.getNumberId());
            turntableCouponEntity.setCouponTime(now);
            turntableCouponEntity.setUserId(userId);
            turntableCouponDao.insert(turntableCouponEntity);
        }else if(turntableCouponEntity.getCouponBlue()<20){
            //计算获得多少张 20分钟一张
            long nowSecond = now.toEpochSecond(ZoneOffset.of("+8"));
            long lastTime = turntableCouponEntity.getCouponTime().toEpochSecond(ZoneOffset.of("+8"));
            long l = nowSecond - lastTime;
            int count = (int) l / (60 * 20);
            int green = turntableCouponEntity.getCouponGreen() + count;
            turntableCouponEntity.setCouponGreen(Math.min(green, 20));
            turntableCouponEntity.setCouponTime(now);
            if(green<20){
                map.put("time",l%(60 * 20));
            }
            turntableCouponDao.updateById(turntableCouponEntity);
        }
        map.put("coupon",turntableCouponEntity);
        return map;
    }
}
