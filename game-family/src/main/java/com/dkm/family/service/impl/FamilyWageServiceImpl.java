package com.dkm.family.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.family.dao.FamilyDetailDao;
import com.dkm.family.dao.FamilyWageDao;
import com.dkm.family.entity.FamilyDetailEntity;
import com.dkm.family.entity.FamilyWageEntity;
import com.dkm.family.service.FamilyWageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: game_project
 * @description: 实现类
 * @author: zhd
 * @create: 2020-06-03 14:36
 **/
@Service
public class FamilyWageServiceImpl implements FamilyWageService {


    @Resource
    private FamilyDetailDao familyDetailDao;

    @Resource
    private FamilyWageDao familyWageDao;

    @Override
    public Map<String,Object> getWageList(Long userId) {
        FamilyDetailEntity familyDetailEntity = familyDetailDao.selectOne(new QueryWrapper<FamilyDetailEntity>().lambda().eq(FamilyDetailEntity::getUserId, userId));
        if(familyDetailEntity==null){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"没有加入家族");
        }
        Map<String,Object> map = new HashMap<>();
        //根据权限设置工资
        if(familyDetailEntity.getIsAdmin()==0){
            //成员工资
            map.put("wage",Stream.of(50000, 50000, 50000, 100000, 100000, 200000, 200000).collect(Collectors.toList()));
        }else if(familyDetailEntity.getIsAdmin()==1){
            //管理员工资
            map.put("wage",Stream.of(150000, 200000, 300000, 400000, 500000, 800000, 950000).collect(Collectors.toList()));
        }else if(familyDetailEntity.getIsAdmin()==2){
            //族长工资
            map.put("wage",Stream.of(200000, 300000, 400000, 500000, 600000, 1000000, 2000000).collect(Collectors.toList()));
        }
        //判断今日是否已领取
        LocalDate now = LocalDate.now();
        FamilyWageEntity familyWageEntity = familyWageDao.selectOne(new QueryWrapper<FamilyWageEntity>().lambda().eq(FamilyWageEntity::getUserId, userId));
        if(familyWageEntity!=null&&familyWageEntity.getFamilyWageTime().isEqual(now)){
            map.put("day", 0);
        }else {
            map.put("day", now.getDayOfWeek().getValue());
        }
        return map;
    }

    @Override
    public void updateUserWage(Integer wage, Long userId) {
        Integer integer = familyDetailDao.updateUserWage(wage, userId);
        if(integer<1){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"领取失败");
        }
    }

}
