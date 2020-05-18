package com.dkm.problem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.UserFeignClient;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.problem.dao.MoneyMapper;
import com.dkm.problem.entity.Money;
import com.dkm.problem.entity.vo.*;
import com.dkm.problem.service.IMoneyService;
import com.dkm.utils.DateUtil;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
      userInfoFeignClient.updateUserInfo(much, user.getId());

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
   public Page<Money> listAllMoney(MoneyPageVo vo) {

      Page<Money> page = new Page<>();
      page.setCurrent(vo.getCurrent());
      page.setSize(vo.getSize());

      LambdaQueryWrapper<Money> wrapper = new LambdaQueryWrapper<Money>()
            .eq(Money::getStatus,0)
            .or()
            .eq(Money::getStatus,1);

      baseMapper.selectPage(page, wrapper);
      return page;
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
      LocalDate start = LocalDate.now();

      String startTime = DateUtil.formatDate(start) + " 00:00:00";

      //一周前的时间
      LocalDate end = start.minusDays(7);

      String endTime = DateUtil.formatDate(end) + " 23:59:59";

      LocalDateTime startDate = DateUtil.parseDateTime(startTime);
      LocalDateTime endDate = DateUtil.parseDateTime(endTime);

      return baseMapper.countHandOutRedEnvelopes(page,status,startDate,endDate);
   }
}
