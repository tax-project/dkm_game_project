package com.dkm.problem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.ParamBo;
import com.dkm.entity.bo.UserHeardUrlBo;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.ResourceFeignClient;
import com.dkm.feign.UserFeignClient;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.problem.dao.MoneyMapper;
import com.dkm.problem.entity.Money;
import com.dkm.problem.entity.bo.MoneyBo;
import com.dkm.problem.entity.bo.MoneyRandomBo;
import com.dkm.problem.entity.vo.*;
import com.dkm.problem.service.IMoneyService;
import com.dkm.utils.DateUtils;
import com.dkm.utils.IdGenerator;
import com.dkm.vilidata.RandomData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author qf
 * @date 2020/5/9
 * @vesion 1.0
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class MoneyServiceImpl extends ServiceImpl<MoneyMapper, Money> implements IMoneyService {

   @Autowired
   private LocalUser localUser;

   @Autowired
   private IdGenerator idGenerator;

   @Autowired
   private UserFeignClient userInfoFeignClient;

   @Autowired
   private RandomData randomData;

   /**
    * 发红包
    * @param vo
    */
   @Override
   public HandOutRedEnvelopesVo handOutRedEnvelopes(MoneyVo vo) {

      //根据金额算出人数上限
      if (vo.getDiamonds() <= vo.getNumber()) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "您发的红包金额对应的人数上限是" +vo.getDiamonds());
      }

      Double single = Double.valueOf(vo.getDiamonds());

      //发红包的一系列奖励
      double redMoney = single / 10;
      int anInt = Integer.parseInt(String.format("%.0f", redMoney * 10 + Math.pow(redMoney - 1, 7 / 6.0) * 2));
      //声望
//      (redMoney*10+(redMoney-1)^(7/6.0)*2)
      Integer renown = anInt;
      //经验
      Integer experience = anInt * 50;
      //金主礼包
      //金币20 声望5 钻石1  生成1-26的随机数
      int random = (int) (Math.random() * 26) + 1;
      BagVo bagVo = new BagVo();
      int diamonds = (int) redMoney*10;
      if (random == 1) {
         //开出钻石
         bagVo.setBagNo(2);
         bagVo.setBagName("钻石");

         //得到最小的钻石
         int minNumber = (int) (diamonds * 0.5);
         //得到最大的钻石
         int maxNumber = diamonds;

         int bagNumber = (int) (Math.random() * (maxNumber - minNumber + 1)) + minNumber;

         bagVo.setBagNumber(bagNumber);
      } else if (random == 2 || random == 3 || random == 4 || random == 5 || random == 6) {
         //开出声望
         bagVo.setBagNo(0);
         bagVo.setBagName("声望");

         //得到最小的声望
         int minRenown = (int) (renown * 0.3);
         //得到最大的声望
         int maxRenown = (int) (renown * 0.5);

         int bagNumber = (int) (Math.random() * (maxRenown - minRenown + 1)) + minRenown;
         bagVo.setBagNumber(bagNumber);
      } else {
         //开出金币
         bagVo.setBagNo(1);
         bagVo.setBagName("金币");

         //得到最小的金币
         int minCoin = anInt * 50;
         //最大的金币
         int maxCoin = anInt * 100;

         int bagNumber = (int) (Math.random() * (maxCoin - minCoin + 1)) + minCoin;

         bagVo.setBagNumber(bagNumber);
      }

      //参与红包活动次数
      //(redMoney/10)^(1/2.0)
      int much = Integer.parseInt(String.format("%.0f", Math.pow(redMoney / 10, 1/2.0)));

      UserLoginQuery user = localUser.getUser();

      //修改用户的红包次数
      userInfoFeignClient.updateUserInfo(much, user.getId(),vo.getDiamonds());

      Money money = new Money();

      money.setId(idGenerator.getNumberId());
      money.setUserId(user.getId());
      money.setDiamonds(vo.getDiamonds());
      money.setNumber(vo.getNumber());
      //题目的总数量
      Integer tNumber = vo.getNumber() * 10;
      //一道题得到的钻石
      Integer mNumber = vo.getDiamonds() / tNumber;

      money.setOneDiamonds(mNumber);
      money.setCreateDate(LocalDateTime.now());
      money.setStatus(0);
      money.setInNumber(0);

      int insert = baseMapper.insert(money);

      if (insert <= 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "发送红包失败");
      }


      //返回获得的奖励
      HandOutRedEnvelopesVo result = new HandOutRedEnvelopesVo();
      result.setRenown(renown);
      result.setExperience(experience);
      result.setBagVo(bagVo);
      result.setRedMuch(much);

      return result;
   }


   @Override
   public  Map<String, Object> listAllMoney(MoneyPageVo vo) {

      UserLoginQuery user = localUser.getUser();

      LambdaQueryWrapper<Money> wrapper = new LambdaQueryWrapper<Money>()
            .eq(Money::getStatus,0)
            .or()
            .eq(Money::getStatus,1)
            .orderByDesc(Money::getCreateDate);

      //查询还未结束的红包
      List<Money> list = baseMapper.selectList(wrapper);

      if (null == list || list.size() == 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "没有发布的红包");
      }

      List<Long> longList = new ArrayList<>();

      int peopleNumber = 0;
      for (Money money : list) {
         //将正在开始的红包算出来
         if (money.getStatus() == 1) {
            peopleNumber += 1;
         }
         //添加进所有抢红包的用户
         longList.add(money.getUserId());
      }
      ParamBo bo = new ParamBo();
      bo.setList(longList);

      //得到用户头像集合
      Result<List<UserHeardUrlBo>> listResult = userInfoFeignClient.queryAllHeardByUserId(bo);

      if (listResult.getCode() != 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "查询用户头像出错了");
      }

      List<UserHeardUrlBo> heardUrlBoList = listResult.getData();

      Map<Long, UserHeardUrlBo> heardMap = heardUrlBoList.stream()
            .collect(Collectors.toMap(UserHeardUrlBo::getUserId, userHeardUrlBo -> userHeardUrlBo
            ));

      //装配返回给前端的分页数据
      List<MoneyBo> resultList = list.stream().map(money -> {
         MoneyBo moneyBo = new MoneyBo();
         BeanUtils.copyProperties(money, moneyBo);
         moneyBo.setHeadUrl(heardMap.get(money.getUserId()).getHeadUrl());
         moneyBo.setUserNickName(heardMap.get(money.getUserId()).getNickName());
         return moneyBo;
      }).collect(Collectors.toList());

      Map<String, Object> map = new HashMap<>(4);
      //分页数据
      map.put("data",resultList);
      //正在进行的红包人数
      map.put("peopleNumber",peopleNumber);
      //返回当前登录人的今日总共抢红包数和已经抢红包数
      Result<UserInfoQueryBo> result = userInfoFeignClient.queryUser(user.getId());

      if (result.getCode() != 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR);
      }

      RedBagVo redBagVo = new RedBagVo();
      //该用户今日已抢次数
      redBagVo.setRedEnvelopesNumber(result.getData().getUserInfoEnvelopeMuch());
      //今日上限次数
      redBagVo.setAllRedEnvelopesNumber(result.getData().getUserInfoAllEnvelopeMuch());

      UserLoginInfoVo loginUserInfo = new UserLoginInfoVo();
      //用户id
      loginUserInfo.setUserId(result.getData().getUserId());
      //登录人昵称
      loginUserInfo.setNickName(result.getData().getWeChatNickName());
      //头像
      loginUserInfo.setHeardUrl(result.getData().getWeChatHeadImgUrl());

      map.put("redMuch",redBagVo);
      map.put("loginUserInfo", loginUserInfo);

      return map;
   }


   /**
    * 修改红包状态
    * @param id
    * @param status
    */
   @Override
   public void updateMoneyStatus(Long id, Integer status) {

      int i = baseMapper.updateMoney(id,status);

      if (i <= 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "修改失败");
      }
   }

   @Override
   public Money queryNumber(Long id) {
      return baseMapper.selectById(id);
   }


   /**
    * 将红包状态改成已完成
    * @param id
    */
   @Override
   public void updateMoney(Long id) {
      Money money = new Money();
      money.setId(id);
      money.setStatus(2);

      baseMapper.updateById(money);
   }

   /**
    * 统计发红包
    * 0--一周内
    * 1--全部
    * @param status
    * @return
    */
   @Override
   public Page<MoneyCountVo> countHandOutRedEnvelopes(Page<MoneyCountVo> page,Integer status) {

      if (status != 0 && status != 1) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "梁广兄弟你的status怕是传错了");
      }

      //当前时间
      LocalDate end = LocalDate.now();

      String startTime = DateUtils.formatDate(end) + " 23:59:59";

      //一周前的时间
      LocalDate start = end.minusDays(7);

      String endTime = DateUtils.formatDate(start) + " 00:00:00";

      LocalDateTime startDate = DateUtils.parseDateTime(endTime);
      LocalDateTime endDate = DateUtils.parseDateTime(startTime);

      //返回统计发红包的数据
      return baseMapper.countHandOutRedEnvelopes(page,status,startDate,endDate);
   }

   @Override
   public MoneyRandomBo queryMoneyRandom() {

      LambdaQueryWrapper<Money> wrapper = new LambdaQueryWrapper<Money>()
            .eq(Money::getStatus,0)
            .or()
            .eq(Money::getStatus,1);

      //查询还未结束的红包
      List<Money> list = baseMapper.selectList(wrapper);

      if (null == list || list.size() == 0) {
         //没有正在回答或未开始的红包
         throw new ApplicationException(CodeType.SERVICE_ERROR, "没有正在举办的红包活动");
      }

      Set<Integer> set = randomData.getList(list.size(), 1);

      Integer index = 0;
      for (Integer integer : set) {
         //得到随机抽取的索引
         index = integer;
      }

      Money money = list.get(index);

      //装配数据返回给前端
      MoneyRandomBo bo = new MoneyRandomBo();
      bo.setId(money.getId());
      bo.setDiamonds(money.getDiamonds());
      bo.setUserId(money.getUserId());

      return bo;
   }
}
