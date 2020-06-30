package com.dkm.knapsack.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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

import java.util.ArrayList;
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
    public TbEquipmentVo selectByBoxId(String boxId) {
        if(StringUtils.isEmpty(boxId)){
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        return tbBoxMapper.selectByBoxId(Long.valueOf(boxId));
    }

    @Override
    public List<TbBox> selectAll() {
        return tbBoxMapper.selectList(null);
    }

    @Override
    public List<TbEquipmentVo> selectByBoxIdTwo(String boxId) {
        if( StringUtils.isEmpty(boxId) ){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "参数不能为空");
        }
        String[] sList = boxId.split(",");
        List<TbEquipmentVo> list=new ArrayList<>();
        for (String aLong : sList) {
            TbEquipmentVo tbEquipmentVo=tbBoxMapper.selectByBoxId(Long.valueOf(aLong));
            list.add(tbEquipmentVo);
        }
        if(list.size()!=0&&list!=null){
            return list;
        }else{
            return null;
        }
    }
}
