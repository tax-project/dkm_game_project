package com.dkm.shop.service.impl;


import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.shop.dao.TbGrowthMapper;
import com.dkm.shop.domain.TbGrowth;
import com.dkm.shop.service.ITbGrowthService;
import com.dkm.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zy
 * @since 2020-06-11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TbGrowthServiceImpl implements ITbGrowthService {
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private LocalUser localUser;
    @Resource
    TbGrowthMapper tbGrowthMapper;

    @Override
    public void insertTbGrowth(TbGrowth tbGrowth) {
        tbGrowth.setTbGid(idGenerator.getNumberId());
        tbGrowth.setUserId(localUser.getUser().getId());
        int rows=tbGrowthMapper.insert(tbGrowth);
        if(rows <= 0){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "增加失败");
        }
    }

    @Override
    public int selectCountMy() {
        TbGrowth tbGrowth=new TbGrowth();
        tbGrowth.setUserId(localUser.getUser().getId());
        return tbGrowthMapper.selectCountMy(tbGrowth);
    }

    @Override
    public int selectCountDj(TbGrowth tbGrowth) {
        tbGrowth.setUserId(localUser.getUser().getId());
        return tbGrowthMapper.selectCountDj(tbGrowth);
    }
}
