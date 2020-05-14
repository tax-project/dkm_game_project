package com.dkm.shop.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.shop.dao.TbDayCheapMapper;
import com.dkm.shop.domain.TbDayCheap;
import com.dkm.shop.service.TbDayCheapService;

import com.dkm.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 每日特惠表 服务实现类
 * </p>
 *
 * @author zy
 * @since 2020-05-09
 */
@Service
public class TbDayCheapServiceImpl implements TbDayCheapService {
    @Resource
    TbDayCheapMapper tbDayCheapMapper;

    @Autowired
    private IdGenerator idGenerator;

    @Override
    public List<TbDayCheap> selectAll() {
        QueryWrapper<TbDayCheap> queryWrapper=new QueryWrapper();
        List<TbDayCheap> list=tbDayCheapMapper.selectList(queryWrapper);
        if(!list.isEmpty()){
            return list;
        }else{
            return null;
        }
    }

    @Override
    public void addTbDayCheap(TbDayCheap tbDayCheap) {

        tbDayCheap.setCheapId(idGenerator.getNumberId());

        int rows = tbDayCheapMapper.insert(tbDayCheap);
        if(rows <= 0){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "增加失败");
        }
    }
}
