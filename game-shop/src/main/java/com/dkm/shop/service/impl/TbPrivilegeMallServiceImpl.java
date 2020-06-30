package com.dkm.shop.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.shop.dao.TbPrivilegeMallMapper;
import com.dkm.shop.domain.TbPrivilegeMall;
import com.dkm.shop.service.ITbPrivilegeMallService;
import com.dkm.utils.IdGenerator;
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
 * @since 2020-06-09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TbPrivilegeMallServiceImpl implements ITbPrivilegeMallService {
    @Autowired
    TbPrivilegeMallMapper tbPrivilegeMallMapper;
    @Autowired
    private IdGenerator idGenerator;
    @Override
    public List<TbPrivilegeMall> selectAll() {
        return tbPrivilegeMallMapper.selectList(null);
    }

    @Override
    public void insert(TbPrivilegeMall tbPrivilegeMall) {
        tbPrivilegeMall.setPriId(idGenerator.getNumberId());
        int rows=tbPrivilegeMallMapper.insert(tbPrivilegeMall);
        if(rows <= 0){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "增加失败");
        }
    }
}
