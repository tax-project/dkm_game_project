package com.dkm.shop.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.shop.dao.TbThedailyMapper;
import com.dkm.shop.domain.TbThedaily;
import com.dkm.shop.service.ITbThedailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zy
 * @since 2020-06-21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TbThedailyServiceImpl  implements ITbThedailyService {
    @Autowired
    TbThedailyMapper tbThedailyMapper;
    @Autowired
    TbThedailyOneServiceImpl tbThedailyOneService;
    @Override
    public List<TbThedaily> selectAll() {
        List<TbThedaily> listOne=new ArrayList<>();
        List<TbThedaily> list=tbThedailyMapper.selectList(null);
        for (TbThedaily tbThedaily : list) {
            if(tbThedaily.getThdIsva()==2){
                int rows=tbThedailyOneService.selectCount(String.valueOf(tbThedaily.getThdId()));
                tbThedaily.setExp1(String.valueOf(20-rows));
            }
            listOne.add(tbThedaily);
        }
        return listOne;
    }

    @Override
    public List<TbThedaily> findById(Long thdId) {
        if( StringUtils.isEmpty(thdId) ){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("thd_id",thdId);
        return tbThedailyMapper.selectList(queryWrapper);
    }
}
