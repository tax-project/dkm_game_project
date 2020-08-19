package com.dkm.plunder.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.config.RedisConfig;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.entity.bo.UserPlunderBo;
import com.dkm.entity.websocket.MsgInfo;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.UserFeignClient;
import com.dkm.good.entity.vo.GoodQueryVo;
import com.dkm.good.service.IGoodsService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.plunder.dao.PlunderMapper;
import com.dkm.plunder.entity.Plunder;
import com.dkm.plunder.entity.bo.PlunderBO;
import com.dkm.plunder.entity.bo.PlunderIdBO;
import com.dkm.plunder.entity.vo.PlunderGoodsVo;
import com.dkm.plunder.entity.vo.PlunderUserGoodVo;
import com.dkm.plunder.entity.vo.PlunderVo;
import com.dkm.plunder.service.IPlunderGoodsService;
import com.dkm.plunder.service.IPlunderService;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

   @Autowired
   private RabbitTemplate rabbitTemplate;

   /**
    *  物品金币
    */
   private String GOOD_NAME = "金币";

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

      if (null == vo.getGoodsIdList() || vo.getGoodsIdList().size() == 0) {
         throw new ApplicationException(CodeType.PARAMETER_ERROR);
      }

      for (PlunderIdBO goods : vo.getGoodsIdList()) {
         PlunderGoodsVo goodsVo = new PlunderGoodsVo();
         goodsVo.setId(idGenerator.getNumberId());
         goodsVo.setGoodId(goods.getGoodId());
         goodsVo.setPlunderId(plunderId);

         plunderGoodsService.insertPlunderGoods(goodsVo);
      }



      //修改体力值
      //减少自己的体力  自己抢别人
      userFeignClient.updateStrength(user.getId(),vo.getGrade());

      MsgInfo msgInfo = new MsgInfo();
      msgInfo.setMsg("掠夺事件");
      msgInfo.setType(6);
      msgInfo.setMsgType(1);
      msgInfo.setToId(vo.getUserId());

      log.info("发送事件通知...");
      rabbitTemplate.convertAndSend("game_event_notice", JSON.toJSONString(msgInfo));
   }



   @Override
   public Map<String, Object> queryPlunderList() {

      UserLoginQuery user = localUser.getUser();

      Result<List<UserPlunderBo>> result = userFeignClient.listUserPlunder();

      if (result.getCode() != 0) {
         log.info("query user <listUserPlunder> feign err.");
         throw new ApplicationException(CodeType.SERVICE_ERROR);
      }

      //得到20条随机用户信息
      List<UserPlunderBo> list = result.getData();

      //如果没有用户返回，则直接返回Null
      if (null == list || list.size() == 0) {
         log.info("query user feign err.");
         throw new ApplicationException(CodeType.SERVICE_ERROR);
      }

      //创建一个装id集合的参数查询产出信息
      List<Long> longList = new ArrayList<>();
      //创建一个排除自己的集合
      List<UserPlunderBo> plunderBoList = new ArrayList<>();
      for (UserPlunderBo bo : list) {

         if (!bo.getUserId().equals(user.getId())) {
            //添加去除自己产出的信息
            longList.add(bo.getUserId());
            plunderBoList.add(bo);
         }
      }

      //根据id集合查询所有产出信息
      List<GoodQueryVo> goodsList = goodsService.queryGoodsList(longList);

      if (null == goodsList || goodsList.size() == 0) {
         log.info("query all produce to user is null.");
         return null;
      }

      //转成map
      //用stream将两个集合进行合并
      Map<Long, List<GoodQueryVo>> goodMap = plunderBoList.stream()
            .collect(Collectors.toMap(UserPlunderBo::getUserId, userPlunderBo ->
                  new ArrayList<>()
            ));

      for (GoodQueryVo vo : goodsList) {
         List<GoodQueryVo> goodQueryVos = goodMap.get(vo.getUserId());
         goodQueryVos.add(vo);
      }

      List<PlunderUserGoodVo> resultList = plunderBoList.stream().map(userPlunderBo -> {
         PlunderUserGoodVo vo = new PlunderUserGoodVo();
         BeanUtils.copyProperties(userPlunderBo, vo);
         vo.setGoodList(goodMap.get(userPlunderBo.getUserId()));
         return vo;
      }).collect(Collectors.toList());

      List<PlunderUserGoodVo> goodVoList = new ArrayList<>();
      for (PlunderUserGoodVo vo : resultList) {
         //排除没有产出的用户
         if (vo.getGoodList() != null && vo.getGoodList().size() > 0) {
            goodVoList.add(vo);
         }
      }

      Map<String,Object> map = new HashMap<>(3);

      //返回集合信息
      map.put("infoList",goodVoList);

      //查询用户信息
      Result<UserInfoQueryBo> queryUser = userFeignClient.queryUser(user.getId());

      if (queryUser.getCode() != 0) {
         log.info("query user <queryUser> feign err.");
         throw new ApplicationException(CodeType.SERVICE_ERROR);
      }

      UserInfoQueryBo userData = queryUser.getData();

      map.put("currentStrength",userData.getUserInfoStrength());
      map.put("allStrength",userData.getUserInfoAllStrength());

      return map;
   }

   @Override
   public PlunderBO getGoodByUserId(Long userId) {

      UserLoginQuery user = localUser.getUser();

      List<GoodQueryVo> goodList = goodsService.getGoodList(userId);

      for (GoodQueryVo vo : goodList) {
         if (GOOD_NAME.equals(vo.getGoodName())) {

            //随机生成100-200的金币
            int number = (int) (Math.random() * (200 - 100 + 1)) + 100;
            vo.setNumber(number);

         } else {
            //随机生成1-3的随机数
            int number = (int) (Math.random() * (3 - 1 + 1)) + 1;
            vo.setNumber(number);
         }
      }

      PlunderBO bo = new PlunderBO();
      bo.setList(goodList);

      //是否是vip
      Result<UserInfoQueryBo> result = userFeignClient.queryUser(user.getId());

      if (result.getCode() != 0) {
         log.info("用户feign查询有误");
         throw new ApplicationException(CodeType.FEIGN_CONNECT_ERROR, "网络繁忙,请稍后再试");
      }

      if (result.getData() != null) {
         bo.setIsVip(result.getData().getUserInfoIsVip());
      }

      return bo;
   }
}
