package com.dkm.box.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dkm.box.dao.AutoSellMapper;
import com.dkm.box.entity.AutoSellEntity;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-08-03 09:42
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class AutoSellEqServiceImpl implements IAutoSellEqService {

    @Resource
    private AutoSellMapper autoSellMapper;

    @Override
    public void setAutoSell(Long userId,String sellInfo) {
        AutoSellEntity autoSellEntity = autoSellMapper.selectOne(new LambdaQueryWrapper<AutoSellEntity>().eq(AutoSellEntity::getUserId, userId));
        if(autoSellEntity==null){
            AutoSellEntity autoSellEntity1 = new AutoSellEntity();
            autoSellEntity1.setAutoSellOrder(sellInfo);
            autoSellEntity1.setUserId(userId);
            if(autoSellMapper.insert(autoSellEntity1)<=0){throw new ApplicationException(CodeType.SERVICE_ERROR);}
        }else {
            autoSellEntity.setAutoSellOrder(sellInfo);
            if(autoSellMapper.updateById(autoSellEntity)<=0){throw new ApplicationException(CodeType.SERVICE_ERROR);}
        }
    }

    @Override
    public List<Integer> getAutoSellInfo(Long userId) {
        AutoSellEntity autoSellEntity = autoSellMapper.selectOne(new LambdaQueryWrapper<AutoSellEntity>().eq(AutoSellEntity::getUserId, userId));
        return autoSellEntity==null?null:JSON.parseArray(autoSellEntity.getAutoSellOrder(), Integer.class);
    }
}
