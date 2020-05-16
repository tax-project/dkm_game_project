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

    /**
     * 根据当前用户查询食物
     * @return
     */
    @Override
    public List<TbEquipmentKnapsackVo> selectFoodId() {
        return tbEquipmentKnapsackMapper.selectFoodId(localUser.getUser().getId());
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
            //得到当前用户的id然后查询出背包的主键 localUser.getUser().getId()
            TbKnapsack tbKnapsack=new TbKnapsack();
            tbKnapsack.setUserId(localUser.getUser().getId());
            List<TbKnapsack> list1=tbKnapsackService.findById(tbKnapsack);
            for (TbKnapsack knapsack : list1) {
                //传入当前用户背包的外键和装备编号
                TbEquipmentKnapsackVo tbEquipmentKnapsack=new TbEquipmentKnapsackVo();
                tbEquipmentKnapsack.setExp1(tbEquipment.getExp1());
                tbEquipmentKnapsack.setKnapsackId(knapsack.getKnapsackId());
                int count=tbEquipmentKnapsackMapper.selectCountMy(tbEquipmentKnapsack);
                if(count>0){
                    //查询为装备上的装备数据
                    List<TbEquipmentVo> list3=tbEquipmentService.selectByEquipmentId(equipmentId);
                    TbEquipmentKnapsackVo tbEquipmentKnapsackVo=new TbEquipmentKnapsackVo();
                    tbEquipmentKnapsackVo.setExp1(tbEquipment.getExp1());
                    tbEquipmentKnapsackVo.setKnapsackId(knapsack.getKnapsackId());
                    //查询已经装备上了的装备数据
                    List<TbEquipmentKnapsackVo> list2=tbEquipmentKnapsackMapper.selectAll(tbEquipmentKnapsackVo);
                    map.put("code",3);
                    map.put("msg","此装备已经装备上了");
                    map.put("dataOne",list3);
                    map.put("dataTwo",list2);
                }else{
                    //查询为装备上的装备数据
                    List<TbEquipmentVo> list3=tbEquipmentService.selectByEquipmentId(equipmentId);
                    map.put("code",2);
                    map.put("dataThree",list3);
                    map.put("msg","此装备没有装备上过");
                }
            }
        }
        return map;
    }

    @Override
    public int selectCountMy(TbEquipmentKnapsackVo tbEquipmentKnapsackVo) {
        return tbEquipmentKnapsackMapper.selectCountMy(tbEquipmentKnapsackVo);
    }

    @Override
    public List<TbEquipmentKnapsackVo> selectAll(TbEquipmentKnapsackVo tbEquipmentKnapsackVo) {
        return tbEquipmentKnapsackMapper.selectAll(tbEquipmentKnapsackVo);
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
    public void updateTekId(Long tekId) {
        if(StringUtils.isEmpty(tekId)){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        //首先根据背包装备表的主键得到装备的外键
        QueryWrapper<TbEquipmentKnapsack> queryWrapper=new QueryWrapper();
        queryWrapper.eq("tek_id",tekId);
        List<TbEquipmentKnapsack> list=tbEquipmentKnapsackMapper.selectList(queryWrapper);

        for (TbEquipmentKnapsack tbEquipmentKnapsack : list) {
            //根据装备外键查询出所属的装备编号
            List<TbEquipmentVo> list1=tbEquipmentService.selectByEquipmentId(tbEquipmentKnapsack.getEquipmentId());
            for (TbEquipmentVo tbEquipmentVo : list1) {
                //得到当前用户的id然后查询出背包的主键 localUser.getUser().getId()
                TbKnapsack tbKnapsack=new TbKnapsack();
                tbKnapsack.setUserId(2L);
                List<TbKnapsack> list2=tbKnapsackService.findById(tbKnapsack);
                for (TbKnapsack knapsack : list2) {
                    //传入当前用户背包的外键和装备编号
                    TbEquipmentKnapsackVo tbEquipmentKnapsackVo=new TbEquipmentKnapsackVo();
                    //得到装备所属的编号 然后查询下用户背包是否有此编号装备
                    tbEquipmentKnapsackVo.setExp1(tbEquipmentVo.getExp1());
                    //得到当前用户的背包外键
                    tbEquipmentKnapsackVo.setKnapsackId(knapsack.getKnapsackId());
                    int count=tbEquipmentKnapsackMapper.selectCountMy(tbEquipmentKnapsackVo);
                    //判断此装备已经装备上了要给他替换
                    if(count>0){

                        TbEquipmentKnapsackVo tbEquipmentKnapsackVoTwo=new TbEquipmentKnapsackVo();
                        tbEquipmentKnapsackVoTwo.setExp1(tbEquipmentVo.getExp1());
                        tbEquipmentKnapsackVoTwo.setKnapsackId(knapsack.getKnapsackId());
                        //查询已经装备上了的装备数据
                        List<TbEquipmentKnapsackVo> list3=tbEquipmentKnapsackMapper.selectAll(tbEquipmentKnapsackVoTwo);
                        for (TbEquipmentKnapsackVo equipmentKnapsackVo : list3) {
                            TbEquipmentKnapsack tbEquipmentKnapsack3=new TbEquipmentKnapsack();
                            tbEquipmentKnapsack3.setTekSell(2);
                            QueryWrapper queryWrapper2=new QueryWrapper();
                            queryWrapper2.eq("tek_id",equipmentKnapsackVo.getTekId());
                            int countTwo=tbEquipmentKnapsackMapper.update(tbEquipmentKnapsack3,queryWrapper2);

                            TbEquipmentKnapsack tbEquipmentKnapsack1=new TbEquipmentKnapsack();
                            tbEquipmentKnapsack1.setTekSell(1);
                            QueryWrapper queryWrapper1=new QueryWrapper();
                            queryWrapper1.eq("tek_id",tekId);
                            int countOne=tbEquipmentKnapsackMapper.update(tbEquipmentKnapsack1,queryWrapper1);

                            if(countOne<=0 && countTwo<=0){
                                //如果失败将回滚
                                throw new ApplicationException(CodeType.PARAMETER_ERROR, "失败");
                            }
                        }
                    }else{
                        TbEquipmentKnapsack tbEquipmentKnapsack1=new TbEquipmentKnapsack();
                        tbEquipmentKnapsack1.setTekSell(1);
                        QueryWrapper queryWrapper1=new QueryWrapper();
                        queryWrapper1.eq("tek_id",tekId);
                        int countOne=tbEquipmentKnapsackMapper.update(tbEquipmentKnapsack1,queryWrapper1);
                        if(countOne<=0){
                            //如果失败将回滚
                            throw new ApplicationException(CodeType.PARAMETER_ERROR, "失败");
                        }
                    }
                }

            }
        }

    }

    @Override
    public void updateIsva(Long tekId,Integer foodNumber) {
        if(StringUtils.isEmpty(tekId) &&StringUtils.isEmpty(foodNumber)){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        QueryWrapper<TbEquipmentKnapsack> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("tek_id",tekId);
        List<TbEquipmentKnapsack> list=tbEquipmentKnapsackMapper.selectList(queryWrapper);
        for (TbEquipmentKnapsack tbEquipmentKnapsack : list) {
            if(foodNumber>tbEquipmentKnapsack.getFoodNumber()){
                //如果失败将回滚
                throw new ApplicationException(CodeType.PARAMETER_ERROR, "数量没有那么多了");
            }else{
                TbEquipmentKnapsack tbEquipmentKnapsack1=new TbEquipmentKnapsack();
                tbEquipmentKnapsack1.setFoodNumber(tbEquipmentKnapsack.getFoodNumber()-foodNumber);
                QueryWrapper queryWrapper1=new QueryWrapper();
                queryWrapper1.eq("tek_id",tekId);
                int rows=tbEquipmentKnapsackMapper.update(tbEquipmentKnapsack1,queryWrapper1);
                if(rows<=0){
                    //如果失败将回滚
                    throw new ApplicationException(CodeType.PARAMETER_ERROR, "失败");
                }else{
                    QueryWrapper<TbEquipmentKnapsack> queryWrapper2=new QueryWrapper<>();
                    queryWrapper2.eq("tek_id",tekId);
                    List<TbEquipmentKnapsack> list2=tbEquipmentKnapsackMapper.selectList(queryWrapper2);
                    for (TbEquipmentKnapsack equipmentKnapsack : list2) {
                        if(equipmentKnapsack.getFoodNumber()<=0){
                            QueryWrapper<TbEquipmentKnapsack> queryWrapper3=new QueryWrapper<>();
                            queryWrapper3.eq("tek_id",tekId);
                            TbEquipmentKnapsack tbEquipmentKnapsack2=new TbEquipmentKnapsack();
                            tbEquipmentKnapsack2.setTekIsva(0);
                            tbEquipmentKnapsackMapper.update(tbEquipmentKnapsack2,queryWrapper3);
                        }
                    }
                }
            }
        }
    }

}
