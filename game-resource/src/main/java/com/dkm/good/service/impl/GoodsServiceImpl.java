package com.dkm.good.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.good.dao.GoodMapper;
import com.dkm.good.entity.Goods;
import com.dkm.good.entity.vo.GoodsVo;
import com.dkm.good.service.IGoodsService;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author qf
 * @date 2020/5/19
 * @vesion 1.0
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsServiceImpl extends ServiceImpl<GoodMapper, Goods> implements IGoodsService {

   @Autowired
   private IdGenerator idGenerator;

   @Override
   public void insertGood(GoodsVo vo) {

      LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<Goods>()
            .eq(Goods::getName,vo.getName());

      Goods goods1 = baseMapper.selectOne(wrapper);

      if (goods1 != null) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "该物品已存在");
      }

      Goods goods = new Goods();
      goods.setId(idGenerator.getNumberId());
      goods.setName(vo.getName());
      goods.setUrl(vo.getUrl());

      int insert = baseMapper.insert(goods);

      if (insert <= 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "增加失败");
      }
   }
}
