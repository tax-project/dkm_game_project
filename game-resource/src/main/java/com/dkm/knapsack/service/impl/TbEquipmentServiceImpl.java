package com.dkm.knapsack.service.impl;

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

    /**
     * 增加装备的接口
     * @param tbEquipmentVo 对应的参数
     */
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

    /**
     * 批量出售的接口
     * @param equipmentId 字符串主键参数
     */
    @Override
    public void listEquipmentId(String equipmentId) {
        if(StringUtils.isEmpty(equipmentId)){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        String[] athleteId = equipmentId.split(",");
      /*  JSONArray obj = JSON.parseArray(equipmentId);
        List<Integer> sList = new ArrayList<Integer>();
        if (obj.size() > 0) {
            for (int i = 0; i < obj.size(); i++) {
                sList.add((Integer) obj.get(i));
            }
        }*/
        //定义一个钱的变量
        Integer money=0;
        for (String s : athleteId) {
            List<TbEquipmentVo> selectByEquipmentId=tbEquipmentService.selectByEquipmentId(Long.valueOf(s));
            for (TbEquipmentVo tbEquipmentVo : selectByEquipmentId) {
                money+=Integer.valueOf(tbEquipmentVo.getExp2());
            }
        }
        IncreaseUserInfoBO increaseUserInfoBO=new IncreaseUserInfoBO();
        increaseUserInfoBO.setUserId(localUser.getUser().getId());
        increaseUserInfoBO.setUserInfoGold(money);
        increaseUserInfoBO.setUserInfoRenown(0);
        increaseUserInfoBO.setUserInfoDiamonds(0);
        userFeignClient.increaseUserInfo(increaseUserInfoBO);
    }

    /**
     * 根据装备主键查询详情
     * @param equipmentId 装备主键
     * @return 返回对应json
     */
    @Override
    public List<TbEquipmentVo> selectByEquipmentId(Long equipmentId) {
        if(StringUtils.isEmpty(equipmentId)){
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        return tbEquipmentMapper.selectByEquipmentId(equipmentId);
    }

    /**
     * 根据编号类型随机查询出一条装备数据
     * @param exp1 参数
     * @return 返回对应的模型数据
     */
    @Override
    public TbEquipmentVo selectByEquipmentIdTwo(String exp1) {
        if(StringUtils.isEmpty(exp1)){
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        return tbEquipmentMapper.selectByEquipmentIdTwo(exp1);
    }
}
