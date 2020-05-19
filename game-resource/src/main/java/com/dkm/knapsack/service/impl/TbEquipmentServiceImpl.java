package com.dkm.knapsack.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.UserFeignClient;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.knapsack.dao.TbEquipmentDetailsMapper;
import com.dkm.knapsack.dao.TbEquipmentMapper;
import com.dkm.knapsack.domain.TbEquipment;
import com.dkm.knapsack.domain.TbEquipmentDetails;
import com.dkm.knapsack.domain.bo.IncreaseUserInfoBO;
import com.dkm.knapsack.domain.vo.TbEquipmentVo;
import com.dkm.knapsack.service.ITbEquipmentService;
import com.dkm.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 装备表 服务实现类
 * </p>
 *
 * @author zy
 * @since 2020-05-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TbEquipmentServiceImpl implements ITbEquipmentService {
    @Autowired
    TbEquipmentMapper tbEquipmentMapper;
    @Autowired
    TbEquipmentDetailsMapper tbEquipmentDetailsMapper;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    ITbEquipmentService tbEquipmentService;
    @Autowired
    private LocalUser localUser;

    @Override
    public void addTbEquipment(TbEquipmentVo tbEquipmentVo) {
        //给装备一个主键
        tbEquipmentVo.setEquipmentId(idGenerator.getNumberId());
        TbEquipment tbEquipment=new TbEquipment();
        BeanUtils.copyProperties(tbEquipmentVo,tbEquipment);
        int rows=tbEquipmentMapper.insert(tbEquipment);
        if(rows <= 0){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "增加失败");
        }else{
            TbEquipmentDetails tbEquipmentDetails=new TbEquipmentDetails();
            BeanUtils.copyProperties(tbEquipmentVo,tbEquipmentDetails);
            tbEquipmentDetails.setEdId(idGenerator.getNumberId());
            //得到装备主键的id传入装备详情当外键
            tbEquipmentDetails.setEquipmentId(tbEquipment.getEquipmentId());
            tbEquipmentDetailsMapper.insert(tbEquipmentDetails);
        }
    }

    @Override
    public void listEquipmentId(String equipmentId) {
        if(StringUtils.isEmpty(equipmentId)){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        JSONArray obj = JSON.parseArray(equipmentId);
        List<Long> sList = new ArrayList<Long>();
        if (obj.size() > 0) {
            for (int i = 0; i < obj.size(); i++) {
                sList.add((Long) obj.get(i));
            }
        }
        //定义一个钱的变量
        String money = "";
        for (Long aLong : sList) {
            List<TbEquipmentVo> selectByEquipmentId=tbEquipmentService.selectByEquipmentId(aLong.longValue());
            for (TbEquipmentVo tbEquipmentVo : selectByEquipmentId) {
                money+=tbEquipmentVo.getExp2();
            }
        }
        IncreaseUserInfoBO increaseUserInfoBO=new IncreaseUserInfoBO();
        increaseUserInfoBO.setUserId(localUser.getUser().getId());
        increaseUserInfoBO.setUserInfoGold(Integer.valueOf(money));
        increaseUserInfoBO.setUserInfoRenown(0);
        increaseUserInfoBO.setUserInfoDiamonds(0);
        userFeignClient.increaseUserInfo(increaseUserInfoBO);
    }

    @Override
    public List<TbEquipmentVo> selectByEquipmentId(Long equipmentId) {
        if(StringUtils.isEmpty(equipmentId)){
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        return tbEquipmentMapper.selectByEquipmentId(equipmentId);
    }
}
