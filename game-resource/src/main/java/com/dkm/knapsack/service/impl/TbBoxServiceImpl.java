package com.dkm.knapsack.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.knapsack.dao.TbBoxMapper;
import com.dkm.knapsack.domain.TbBox;
import com.dkm.knapsack.domain.vo.TbEquipmentVo;
import com.dkm.knapsack.service.ITbBoxService;
import com.dkm.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 宝箱表 服务实现类
 * </p>
 *
 * @author zy
 * @since 2020-05-14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TbBoxServiceImpl  implements ITbBoxService {
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    TbBoxMapper tbBoxMapper;
    @Override
    public void addTbBox(TbBox tbBox) {
        tbBox.setBoxId(idGenerator.getNumberId());
        int rows=tbBoxMapper.insert(tbBox);
        if(rows <= 0){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "增加失败");
        }
    }

    @Override
    public TbEquipmentVo selectByBoxId(Long boxId) {
        if(StringUtils.isEmpty(boxId)){
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        return tbBoxMapper.selectByBoxId(boxId);
    }

    @Override
    public List<TbBox> selectAll() {
        QueryWrapper<TbBox> queryWrapper=new QueryWrapper();
        queryWrapper.eq("box_type",1);
        return tbBoxMapper.selectList(queryWrapper);
    }
}
