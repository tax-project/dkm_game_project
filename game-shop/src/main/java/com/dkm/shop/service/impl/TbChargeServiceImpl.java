package com.dkm.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.config.RedisConfig;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.shop.dao.TbChargeMapper;
import com.dkm.shop.domain.TbCharge;
import com.dkm.shop.service.TbChargeService;
import com.dkm.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 首充豪礼表 服务实现类
 * </p>
 *
 * @author zy
 * @since 2020-05-10
 */
@Service
public class TbChargeServiceImpl implements TbChargeService {
    @Resource
    TbChargeMapper tbChargeMapper;

    @Autowired
    private IdGenerator idGenerator;


    @Override
    public List<TbCharge> selectAll() {
        QueryWrapper<TbCharge>  queryWrapper=new QueryWrapper();
        List<TbCharge> list=tbChargeMapper.selectList(queryWrapper);
        if(!StringUtils.isEmpty(list)){
            return list;
        }else{
            return null;
        }
    }


    @Override
    public void addTbCharge(TbCharge tbCharges) {
        Date date=new Date();
        tbCharges.setCreateTime(date);
        tbCharges.setChargeId(idGenerator.getNumberId());

        int rows = tbChargeMapper.insert(tbCharges);
        if(rows <= 0){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "增加失败");
        }
    }




}
