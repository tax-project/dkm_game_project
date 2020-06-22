package com.dkm.shop.service.impl;


import com.dkm.shop.dao.TbAccumulatedMapper;
import com.dkm.shop.domain.TbAccumulated;
import com.dkm.shop.service.ITbAccumulatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class TbAccumulatedServiceImpl implements ITbAccumulatedService {
    @Autowired
    TbAccumulatedMapper tbAccumulatedMapper;
    @Override
    public List<TbAccumulated> selectAll() {
        return tbAccumulatedMapper.selectList(null);
    }
}
