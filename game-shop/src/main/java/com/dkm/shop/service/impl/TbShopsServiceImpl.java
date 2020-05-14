package com.dkm.shop.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.shop.dao.TbShopsMapper;
import com.dkm.shop.domain.TbShops;
import com.dkm.shop.service.TbShopsService;
import com.dkm.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author zy
 * @since 2020-05-09
 */
@Service
public class TbShopsServiceImpl implements TbShopsService {
    @Resource
    TbShopsMapper tbShopsMapper;

    @Autowired
    private IdGenerator idGenerator;
    @Override
    public List<TbShops> selectAll() {
        QueryWrapper<TbShops> queryWrapper=new QueryWrapper();
        List<TbShops> list=tbShopsMapper.selectList(queryWrapper);
        if(list.isEmpty()){
            return null;
        }else{
            return list;
        }
    }

    @Override
    public void addTbShops(TbShops tbShops) {
        tbShops.setShopId(idGenerator.getNumberId());
        int rows = tbShopsMapper.insert(tbShops);
        if(rows <= 0){
            //如果失败将回滚
            throw new ApplicationException(CodeType.PARAMETER_ERROR, "增加失败");
        }
    }
}
