package com.dkm.box.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dkm.backpack.dao.BackpackMapper;
import com.dkm.backpack.dao.EquipmentMapper;
import com.dkm.backpack.entity.bo.AutoSellEqIdInfo;
import com.dkm.box.dao.AutoSellMapper;
import com.dkm.box.entity.AutoSellEntity;
import com.dkm.box.service.IAutoSellEqService;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Resource
    private EquipmentMapper equipmentMapper;

    @Resource
    private BackpackMapper backpackMapper;

    @Override
    public void setAutoSell(Long userId,String sellInfo) {
        //查询自动出售的信息
        //根据用户id
        AutoSellEntity autoSellEntity = autoSellMapper.selectOne(new LambdaQueryWrapper<AutoSellEntity>()
              .eq(AutoSellEntity::getUserId, userId));
        if(autoSellEntity==null){
            AutoSellEntity autoSellEntity1 = new AutoSellEntity();
            autoSellEntity1.setAutoSellOrder(sellInfo);
            autoSellEntity1.setUserId(userId);
            //添加自动出售信息
            if(autoSellMapper.insert(autoSellEntity1)<=0){throw new ApplicationException(CodeType.SERVICE_ERROR);}
        }else {
            autoSellEntity.setAutoSellOrder(sellInfo);
            //有  修改自动出售信息
            if(autoSellMapper.updateById(autoSellEntity)<=0){throw new ApplicationException(CodeType.SERVICE_ERROR);}
        }
        //根据用户Id查询信息
        List<AutoSellEqIdInfo> autoSell = equipmentMapper.getAutoSell(userId);
        if(autoSell!=null&&autoSell.size()!=0){
            List<Long> ids = new ArrayList<>();
            //装配背包id集合
            autoSell.forEach(a->{
                if(sellInfo.contains(a.getNeedGrade()/5+1+"")){
                    ids.add(a.getBackpackId());
                }
            });
            if(ids!=null&&ids.size()!=0){
                //根据id集合进行批量删除背包和小黑屋的信息
                int i = equipmentMapper.deleteBatchIds(ids);
                int i1 = backpackMapper.deleteBatchIds(ids);
                if(i<=0||i1<=0||i!=i1){throw new ApplicationException(CodeType.SERVICE_ERROR,"出现了一点问题");}
            }
        }
    }

    @Override
    public List<Integer> getAutoSellInfo(Long userId) {
        //根据用户id查询一条数据
        AutoSellEntity autoSellEntity = autoSellMapper.selectOne(new LambdaQueryWrapper<AutoSellEntity>()
              .eq(AutoSellEntity::getUserId, userId));
        return autoSellEntity==null?null:JSON.parseArray(autoSellEntity.getAutoSellOrder(), Integer.class);
    }

}
