package com.dkm.plunder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.plunder.dao.PlunderMapper;
import com.dkm.plunder.entity.Plunder;
import com.dkm.plunder.entity.vo.PlunderGoodsVo;
import com.dkm.plunder.entity.vo.PlunderVo;
import com.dkm.plunder.service.IPlunderGoodsService;
import com.dkm.plunder.service.IPlunderService;
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
public class PlunderServiceImpl extends ServiceImpl<PlunderMapper, Plunder> implements IPlunderService {

   @Autowired
   private IdGenerator idGenerator;

   @Autowired
   private LocalUser localUser;

   @Autowired
   private IPlunderGoodsService plunderGoodsService;

   @Override
   public void insertPlunder(PlunderVo vo) {

      UserLoginQuery user = localUser.getUser();

      Plunder plunder = new Plunder();
      Long plunderId = idGenerator.getNumberId();

      plunder.setId(plunderId);
      plunder.setFromId(user.getId());
      plunder.setToId(vo.getUserId());

      int insert = baseMapper.insert(plunder);

      if (insert <= 0) {
         log.info("增加掠夺表失败");
         throw new ApplicationException(CodeType.SERVICE_ERROR);
      }

      PlunderGoodsVo goodsVo = new PlunderGoodsVo();
      goodsVo.setId(idGenerator.getNumberId());
      goodsVo.setGoodId(vo.getGoodsId());
      goodsVo.setPlunderId(plunderId);

      plunderGoodsService.insertPlunderGoods(goodsVo);
   }
}
