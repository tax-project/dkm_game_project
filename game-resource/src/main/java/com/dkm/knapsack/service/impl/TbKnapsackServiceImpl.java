package com.dkm.knapsack.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.knapsack.dao.TbKnapsackMapper;
import com.dkm.knapsack.domain.TbKnapsack;
import com.dkm.knapsack.service.ITbKnapsackService;
import com.dkm.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zy
 * @since 2020-05-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TbKnapsackServiceImpl implements ITbKnapsackService {
    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    TbKnapsackMapper tbKnapsackMapper;
    @Autowired
    LocalUser localUser;
    @Override
    public void updateByPrimaryKeySelective(TbKnapsack tbKnapsack) {
        if(StringUtils.isEmpty(tbKnapsack.getKnapsackCapacity())){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        tbKnapsack.setUserId(localUser.getUser().getId());
        int rows=tbKnapsackMapper.updateByPrimaryKeySelective(tbKnapsack);
        if(rows <= 0){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "修改失败");
        }
    }

    @Override
    public void addTbKnapsack(TbKnapsack tbKnapsack) {
        tbKnapsack.setKnapsackId(idGenerator.getNumberId());
        int rows=tbKnapsackMapper.insert(tbKnapsack);
        if(rows <= 0){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "增加失败");
        }
    }

    @Override
    public List<TbKnapsack> findById(TbKnapsack tbKnapsack) {
        QueryWrapper<TbKnapsack> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",tbKnapsack.getUserId());
        return tbKnapsackMapper.selectList(queryWrapper);
    }

    @Override
    public TbKnapsack selectByIdTwo(Long userId) {
        return tbKnapsackMapper.selectByIdTwo(userId);
    }
}
