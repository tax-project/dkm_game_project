package com.dkm.housekeeper.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.housekeeper.dao.HousekeeperMapper;
import com.dkm.housekeeper.entity.HousekeeperEntity;
import com.dkm.housekeeper.service.HousekeeperService;
import com.dkm.utils.DateUtil;
import com.dkm.utils.IdGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhd
 * @date 2020/5/8 16:41
 */
@Service
public class HousekeeperServiceImpl implements HousekeeperService {

    @Resource
    private HousekeeperMapper housekeeperMapper;
    @Resource
    private IdGenerator idGenerator;

    /**
     * 开通、续费
     * @param userId
     * @param money
     * @return
     */
    @Override
    public int openHousekeeper(Long userId, BigDecimal money) {
        QueryWrapper<HousekeeperEntity> query= new QueryWrapper<>();
        HousekeeperEntity selectOne = housekeeperMapper.selectOne(query.lambda().eq(HousekeeperEntity::getUserId,userId));
        LocalDateTime now = LocalDateTime.now();
        if(selectOne!=null){
            //判断是否续费
            selectOne.setOrderTime(now);
            selectOne.setHousekeeperMoney(money);
            selectOne.setExpireTime(selectOne.getExpireTime().isBefore(now)?now.minusDays(-30):selectOne.getExpireTime().minusDays(-30));
            return  housekeeperMapper.updateById(selectOne);
        }else {
            //新开管家
            HousekeeperEntity housekeeper = new HousekeeperEntity();
            housekeeper.setUserId(userId);
            housekeeper.setIsEffective(1);
            housekeeper.setHousekeeperMoney(money);
            housekeeper.setOrderTime(LocalDateTime.now());
            housekeeper.setHousekeeperId(idGenerator.getNumberId());
            housekeeper.setExpireTime(now.minusDays(-30));
            return housekeeperMapper.insert(housekeeper);
        }
    }

    /**
     * 管家到期信息
     * @param userId
     * @return
     */
    @Override
    public Map<String,Object> remnantDays(Long userId) {
        QueryWrapper<HousekeeperEntity> query= new QueryWrapper<>();
        Map<String,Object> result = new HashMap<>();
        //返回管家时间信息
        HousekeeperEntity selectOne = housekeeperMapper.selectOne(query.lambda().eq(HousekeeperEntity::getUserId, userId));
        if(selectOne!=null||selectOne.getIsEffective()!=1){
            //管家过期
            LocalDateTime now = LocalDateTime.now();
            if(selectOne.getExpireTime().isBefore(now)){
                selectOne.setIsEffective(0);
                housekeeperMapper.updateById(selectOne);
                throw new  ApplicationException(CodeType.SERVICE_ERROR,"管家过期");
            }
            //返回管家时间
            result.put("reDays",selectOne.getExpireTime().toLocalDate().toEpochDay()-now.toLocalDate().toEpochDay());
            result.put("reTime",DateUtil.formatDateTime(selectOne.getExpireTime()).replace("-","/"));
            result.put("endWorkTime",selectOne.getEndWorkTime()==null?null:DateUtil.formatDateTime(selectOne.getEndWorkTime()).replace("-","/"));
            result.put("startWorkTime",selectOne.getStartWorkTime()==null?null:DateUtil.formatDateTime(selectOne.getStartWorkTime()).replace("-","/"));
            return result;
        }else {
            throw new ApplicationException(CodeType.SERVICE_ERROR,"未找到数据");
        }
    }

    /**
     * 管家时间
     * @param userId
     * @return
     */
    @Override
    public Map<String,String> updateTime(Long userId) {
        QueryWrapper<HousekeeperEntity> query= new QueryWrapper<>();
        HousekeeperEntity selectOne = housekeeperMapper.selectOne(query.lambda().eq(HousekeeperEntity::getUserId, userId));
        LocalDateTime now = LocalDateTime.now();
        selectOne.setEndWorkTime(now.minusHours(-8));
        //第二天12点时间
        selectOne.setStartWorkTime(DateUtil.parseDateTime(DateUtil.formatDate(now.toLocalDate().minusDays(-1))+" 12:00:00"));
        Map<String,String> result = new HashMap<>();
        if(housekeeperMapper.updateById(selectOne)>=0){
            result.put("endWorkTime",DateUtil.formatDateTime(selectOne.getEndWorkTime()).replace("-","/"));
            result.put("startWorkTime",DateUtil.formatDateTime(selectOne.getStartWorkTime()).replace("-","/"));
            return result;
        }else {
            throw new ApplicationException(CodeType.SERVICE_ERROR,"操作失败");
        }
    }
}
