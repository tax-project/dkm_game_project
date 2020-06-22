package com.dkm.shop.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.shop.dao.TbAccumulatedOneMapper;
import com.dkm.shop.domain.TbAccumulatedOne;
import com.dkm.shop.service.ITbAccumulatedOneService;
import com.dkm.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

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
public class TbAccumulatedOneServiceImpl  implements ITbAccumulatedOneService {
    @Resource
    TbAccumulatedOneMapper tbAccumulatedOneMapper;
    @Autowired
    LocalUser localUser;
    @Autowired
    IdGenerator idGenerator;
    @Override
    public void insert(String talId) {
        if( StringUtils.isEmpty(talId) ){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        TbAccumulatedOne tbAccumulatedOne=new TbAccumulatedOne();
        tbAccumulatedOne.setTalId(Long.valueOf(talId));
        tbAccumulatedOne.setUserId(localUser.getUser().getId());
        tbAccumulatedOne.setTaloId(idGenerator.getNumberId());
        int rows=tbAccumulatedOneMapper.insert(tbAccumulatedOne);
        if(rows <= 0){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "增加失败");
        }
    }

    @Override
    public int selectCount(String talId) {
        if( StringUtils.isEmpty(talId) ){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("tal_id",talId);
        queryWrapper.eq("user_id",localUser.getUser().getId());
        int rows=tbAccumulatedOneMapper.selectCount(queryWrapper);
        if(rows>0){
            return 1;
        }else{
            return rows;
        }
    }
}
