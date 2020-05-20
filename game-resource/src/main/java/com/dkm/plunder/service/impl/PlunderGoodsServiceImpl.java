package com.dkm.plunder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.plunder.dao.PlunderGoodsMapper;
import com.dkm.plunder.entity.PlunderGoods;
import com.dkm.plunder.entity.vo.PlunderGoodsVo;
import com.dkm.plunder.service.IPlunderGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/19
 * @vesion 1.0
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class PlunderGoodsServiceImpl extends ServiceImpl<PlunderGoodsMapper, PlunderGoods> implements IPlunderGoodsService {


   @Override
   public void insertAllPlunderGoods(List<PlunderGoodsVo> list) {

      Integer integer = baseMapper.insertAllPlunderGoods(list);

      if (integer <= 0) {
         log.info("批量增加掠夺物品表失败");
         throw new ApplicationException(CodeType.SERVICE_ERROR);
      }
   }

   @Override
   public void insertPlunderGoods(PlunderGoodsVo vo) {
      PlunderGoods plunderGoods = new PlunderGoods();
      BeanUtils.copyProperties(vo,plunderGoods);

      int insert = baseMapper.insert(plunderGoods);

      if (insert <= 0) {
         log.info("增加掠夺物品表失败");
         throw new ApplicationException(CodeType.SERVICE_ERROR);
      }
   }
}
