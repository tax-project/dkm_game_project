package com.dkm.plunder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.entity.bo.UserPlunderBo;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.UserFeignClient;
import com.dkm.good.entity.Goods;
import com.dkm.good.entity.vo.GoodQueryVo;
import com.dkm.good.service.IGoodsService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.plunder.dao.PlunderMapper;
import com.dkm.plunder.entity.Plunder;
import com.dkm.plunder.entity.vo.PlunderGoodsVo;
import com.dkm.plunder.entity.vo.PlunderResultVo;
import com.dkm.plunder.entity.vo.PlunderVo;
import com.dkm.plunder.service.IPlunderGoodsService;
import com.dkm.plunder.service.IPlunderService;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
   private UserFeignClient userFeignClient;

   @Autowired
   private IGoodsService goodsService;

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


      //修改体力值
      //减少自己的体力  自己抢别人
      userFeignClient.updateStrength(user.getId(),vo.getGrade());
   }



   @Override
   public Map<String, Object> queryPlunderList() {

      UserLoginQuery user = localUser.getUser();

      Result<List<UserPlunderBo>> result = userFeignClient.listUserPlunder();

      if (result.getCode() != 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "feign有误");
      }

      //得到20条随机用户信息
      List<UserPlunderBo> list = result.getData();

      List<Long> longList = new ArrayList<>();
      for (UserPlunderBo bo : list) {

         if (!bo.getUserId().equals(user.getId())) {
            longList.add(bo.getUserId());
         }
      }

      List<GoodQueryVo> goodsList = goodsService.queryGoodsList(longList);

      Map<String,Object> map = new HashMap<>();

      map.put("userInfo",list);
      map.put("goodInfo",goodsList);


      //查询用户信息
      Result<UserInfoQueryBo> queryUser = userFeignClient.queryUser(user.getId());

      if (queryUser.getCode() != 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "feign有误");
      }

      UserInfoQueryBo userData = queryUser.getData();

      map.put("currentStrength",userData.getUserInfoStrength());
      map.put("allStrength",userData.getUserInfoAllStrength());

      return map;
   }

   @Override
   public List<GoodQueryVo> getGoodByUserId(Long userId) {
      return goodsService.getGoodList(userId);
   }
}
