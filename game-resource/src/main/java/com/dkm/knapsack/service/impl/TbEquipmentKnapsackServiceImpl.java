package com.dkm.knapsack.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.knapsack.dao.TbEquipmentKnapsackMapper;
import com.dkm.knapsack.dao.TbEquipmentMapper;
import com.dkm.knapsack.domain.TbEquipment;
import com.dkm.knapsack.domain.TbEquipmentKnapsack;
import com.dkm.knapsack.domain.TbKnapsack;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVo;
import com.dkm.knapsack.domain.vo.TbEquipmentVo;
import com.dkm.knapsack.service.ITbEquipmentKnapsackService;
import com.dkm.knapsack.service.ITbEquipmentService;
import com.dkm.knapsack.service.ITbKnapsackService;
import com.dkm.utils.IdGenerator;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zy
 * @since 2020-05-14
 */
@Service
public class TbEquipmentKnapsackServiceImpl implements ITbEquipmentKnapsackService {
    @Autowired
    TbEquipmentKnapsackMapper tbEquipmentKnapsackMapper;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private LocalUser localUser;
    @Autowired
    ITbKnapsackService tbKnapsackService;
    @Autowired
    TbEquipmentMapper tbEquipmentMapper;
    @Autowired
    ITbEquipmentService tbEquipmentService;
    @Override
    public List<TbEquipmentKnapsackVo> selectUserId() {
        return tbEquipmentKnapsackMapper.selectUserId(localUser.getUser().getId());
    }

    @Override
    public void addTbEquipmentKnapsack(TbEquipmentKnapsack tbEquipmentKnapsack) {
        TbKnapsack tbKnapsack=new TbKnapsack();
        tbKnapsack.setUserId(localUser.getUser().getId());
        tbEquipmentKnapsack.setKnapsackId(idGenerator.getNumberId());
        List<TbKnapsack> list=tbKnapsackService.findById(tbKnapsack);

        if(!StringUtils.isEmpty(list)){
            for (TbKnapsack knapsack : list) {
                //传入背包主键
                tbEquipmentKnapsack.setKnapsackId(knapsack.getKnapsackId());
            }
            int rows=tbEquipmentKnapsackMapper.insert(tbEquipmentKnapsack);
            if(rows <= 0){
                //如果失败将回滚
                throw new ApplicationException(CodeType.PARAMETER_ERROR, "增加失败");
            }
        }else{
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "该用户没有背包");
        }


    }

    @Override
    public void deleteTbEquipment(Long tekId,Integer tekMoney) {
        if(StringUtils.isEmpty(tekId)){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        TbEquipmentKnapsack tbEquipmentKnapsack=new TbEquipmentKnapsack();
        tbEquipmentKnapsack.setTekIsva(0);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("tek_id",tekId);
        //tekMoney 这里代表用户金币要增加的接口

        int rows=tbEquipmentKnapsackMapper.update(tbEquipmentKnapsack,queryWrapper);
        if(rows <= 0){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "出售失败");
        }
    }

    @Override
    public Map<String,Object> findById(Long equipmentId) {
        Map<String,Object> map=new HashMap<>();
        if(StringUtils.isEmpty(equipmentId)){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        QueryWrapper<TbEquipment> queryWrapper=new QueryWrapper();
        queryWrapper.eq("equipment_id",equipmentId);
        List<TbEquipment> list=tbEquipmentMapper.selectList(queryWrapper);
        for (TbEquipment tbEquipment : list) {
            int count=tbEquipmentKnapsackMapper.selectCountMy(tbEquipment.getExp1());
            if(count>0){
                //查询为装备上的装备数据
                List<TbEquipmentVo> list1=tbEquipmentService.selectByEquipmentId(equipmentId);
                //查询已经装备上了的装备数据
                List<TbEquipmentKnapsackVo> list2=tbEquipmentKnapsackMapper.selectAll(tbEquipment.getExp1());
                map.put("code",3);
                map.put("msg","此装备已经装备上了");
                map.put("dataOne",list1);
                map.put("dataTwo",list2);
            }else{
                map.put("code",2);
                map.put("msg","此装备没有装备上过");
            }
        }
        return map;
    }

    @Override
    public int selectCountMy(String exp1) {
        return tbEquipmentKnapsackMapper.selectCountMy(exp1);
    }

    @Override
    public List<TbEquipmentKnapsackVo> selectAll(String exp1) {
        return tbEquipmentKnapsackMapper.selectAll(exp1);
    }

    @Override
    public void updateSell(Long tekId) {
        if(StringUtils.isEmpty(tekId)){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        TbEquipmentKnapsack tbEquipmentKnapsack=new TbEquipmentKnapsack();
        QueryWrapper queryWrapper=new QueryWrapper();

        tbEquipmentKnapsack.setTekSell(2);
        queryWrapper.eq("tek_id",tekId);
        int rows=tbEquipmentKnapsackMapper.update(tbEquipmentKnapsack,queryWrapper);
        if(rows <= 0){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "卸下失败");
        }
    }

    @Override
    public void uodateTekId(Long tekId) {
   /*     if(StringUtils.isEmpty(tekId)){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        QueryWrapper<TbEquipment> queryWrapper=new QueryWrapper();
        queryWrapper.eq("equipment_id",equipmentId);
        List<TbEquipment> list=tbEquipmentMapper.selectList(queryWrapper);
    }*/

}
