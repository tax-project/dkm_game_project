package com.dkm.knapsack.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.knapsack.dao.TbBoxMapper;
import com.dkm.knapsack.dao.TbEquipmentKnapsackMapper;
import com.dkm.knapsack.dao.TbEquipmentMapper;
import com.dkm.knapsack.domain.TbBox;
import com.dkm.knapsack.domain.TbEquipment;
import com.dkm.knapsack.domain.TbKnapsack;
import com.dkm.knapsack.domain.vo.TbEquipmentKnapsackVo;
import com.dkm.knapsack.domain.vo.TbEquipmentVo;
import com.dkm.knapsack.service.ITbBoxService;
import com.dkm.knapsack.service.ITbEquipmentKnapsackService;
import com.dkm.knapsack.service.ITbEquipmentService;
import com.dkm.knapsack.service.ITbKnapsackService;
import com.dkm.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 宝箱表 服务实现类
 * </p>
 *
 * @author zy
 * @since 2020-05-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TbBoxServiceImpl  implements ITbBoxService {
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    TbBoxMapper tbBoxMapper;
    @Autowired
    TbEquipmentKnapsackMapper tbEquipmentKnapsackMapper;
    @Autowired
    ITbKnapsackService tbKnapsackService;
    @Autowired
    private LocalUser localUser;
    @Autowired
    ITbEquipmentService tbEquipmentService;
    @Autowired
    ITbEquipmentKnapsackService tbEquipmentKnapsackService;
    @Autowired
    TbEquipmentMapper tbEquipmentMapper;
    /**
     * 增加宝箱的方法
     * @param tbBox 宝箱的模型
     */
    @Override
    public void addTbBox(TbBox tbBox) {
        tbBox.setBoxId(idGenerator.getNumberId());
        int rows=tbBoxMapper.insert(tbBox);
        if(rows <= 0){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "增加失败");
        }
    }

    /**
     *  根据宝箱的主键得到一个新装备
     * @param boxId 宝箱的主键
     * @return 返回一个新装备的对象
     */
    @Override
    public TbEquipmentVo selectByBoxId(String boxId) {
        if(StringUtils.isEmpty(boxId)){
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        return tbBoxMapper.selectByBoxId(Long.valueOf(boxId));
    }

    /**
     * 查询出所有宝箱 根据宝箱的类型 升序
     * @return 返回宝箱数据
     */
    @Override
    public List<TbBox> selectAll() {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.orderByAsc("box_type");
        return tbBoxMapper.selectList(queryWrapper);
    }

    /**
     *
     * @param boxId 根据前端传过来的字符串id批量查询出宝箱对应的随机装备
     *
     * @return 返回一个list的map集合 自动比对 这个新开的装备有没有装备上去过
     * dataOne 为开出的新装备数据   dataTwo为已经装备上去的装备
     * dataThree 为从来没有装备上的新装备
     */
    @Override
    public List<Map> selectByBoxIdTwo(String boxId) {
        if( boxId==null &&"".equals(boxId) ){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        String[] sList = boxId.split(",");
        List<TbEquipmentVo> list=new ArrayList<>();
        for (String aLong : sList) {
            TbEquipmentVo tbEquipmentVo=tbBoxMapper.selectByBoxId(Long.valueOf(aLong));
            list.add(tbEquipmentVo);
        }
        //得到当前用户的id然后查询出背包的主键 localUser.getUser().getId()
        TbKnapsack tbKnapsack=tbKnapsackService.selectByIdTwo(localUser.getUser().getId());
        List<Map> listMap=new ArrayList<>();

            for (int i=0;i<list.size();i++) {
                TbEquipmentVo tbEquipmentVo=list.get(i);
                QueryWrapper<TbEquipment> queryWrapper=new QueryWrapper();
                queryWrapper.eq("equipment_id",tbEquipmentVo.getEquipmentId());
                List<TbEquipment> listTwo=tbEquipmentMapper.selectList(queryWrapper);
                for (int j=0;j<listTwo.size();j++) {
                    TbEquipment tbEquipment=listTwo.get(j);
                        //传入当前用户背包的外键和装备编号
                        TbEquipmentKnapsackVo tbEquipmentKnapsack=new TbEquipmentKnapsackVo();
                        tbEquipmentKnapsack.setExp1(tbEquipment.getExp1());
                        tbEquipmentKnapsack.setKnapsackId(tbKnapsack.getKnapsackId());
                        int count=tbEquipmentKnapsackMapper.selectCountMy(tbEquipmentKnapsack);
                        if(count>0){
                            //查询为装备上的装备数据
                            TbEquipmentVo list3=tbEquipmentService.selectByEquipmentIdTwo(tbEquipment.getExp1());
                            TbEquipmentKnapsackVo tbEquipmentKnapsackVo=new TbEquipmentKnapsackVo();
                            tbEquipmentKnapsackVo.setExp1(tbEquipment.getExp1());
                            tbEquipmentKnapsackVo.setKnapsackId(tbKnapsack.getKnapsackId());
                            //查询已经装备上了的装备数据
                            List<TbEquipmentKnapsackVo> list2=tbEquipmentKnapsackService.selectAll(tbEquipmentKnapsackVo);
                            Map<String,Object> map=new HashMap<>();
                            map.put("code",3);
                            map.put("msg","此装备已经装备上了");
                            map.put("dataOne",list3);
                            map.put("dataTwo",list2);
                            listMap.add(map);
                        }else{
                            Map<String,Object> map=new HashMap<>();
                            //查询为装备上的装备数据
                            List<TbEquipmentVo> list3=tbEquipmentService.selectByEquipmentId(tbEquipmentVo.getEquipmentId());
                            map.put("code",2);
                            map.put("dataThree",list3);
                            map.put("msg","此装备没有装备上过");
                            listMap.add(map);
                        }
                }

            }

        return listMap;
    }

    /**
     * 根据前端传过来的宝箱的主键字符串 得到开出新装备的数据
     * @param boxId 宝箱字符串id
     * @return
     */
    @Override
    public List<TbEquipmentVo> selectByBoxIdThree(String boxId) {
        if( boxId==null &&"".equals(boxId) ){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        String[] sList = boxId.split(",");
        List<TbEquipmentVo> list=new ArrayList<>();
        for (String aLong : sList) {
            TbEquipmentVo tbEquipmentVo=tbBoxMapper.selectByBoxId(Long.valueOf(aLong));
            list.add(tbEquipmentVo);
        }
        if(list.size()!=0&&list!=null){
            return list;
        }else{
            return null;
        }
    }
}
