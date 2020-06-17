package com.dkm.problem.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.UserFeignClient;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.problem.dao.ScoreMapper;
import com.dkm.problem.entity.Money;
import com.dkm.problem.entity.Score;
import com.dkm.problem.entity.vo.MoneyCountVo;
import com.dkm.problem.entity.vo.ScoreListVo;
import com.dkm.problem.entity.vo.ScoreVo;
import com.dkm.problem.service.IMoneyService;
import com.dkm.problem.service.IScoreService;
import com.dkm.utils.DateUtils;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qf
 * @date 2020/5/10
 * @vesion 1.0
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements IScoreService {

   @Autowired
   private LocalUser localUser;

   @Autowired
   private IdGenerator idGenerator;

   @Autowired
   private IMoneyService moneyService;

   @Autowired
   private UserFeignClient userFeignClient;

   /**
    * *  增加用户积分表
    *    返回金额
    *    修改红包状态
    * @param moneyId
    * @param score
    * @return
    */
   @Override
   public ScoreVo insertScore(Long moneyId, Integer score) {

      UserLoginQuery user = localUser.getUser();

      //返回金额
      Money money = moneyService.queryNumber(moneyId);

      if (money == null) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "传的moneyId有误");
      }

      Double single = Double.valueOf(score * money.getOneDiamonds());

      Double price = single / 10;

      ScoreVo vo = new ScoreVo();
      vo.setPrice(price);

      Score score1 = new Score();
      score1.setId(idGenerator.getNumberId());
      score1.setCreateDate(LocalDateTime.now());
      score1.setMoneyId(moneyId);
      score1.setPrice(price);
      score1.setScore(score);

      score1.setUserId(user.getId());

      int insert = baseMapper.insert(score1);

      if (insert <= 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "增加失败");
      }

      if (money.getStatus() == 1) {
         //修改红包状态
         moneyService.updateMoney(moneyId);
      }


      Result<UserInfoQueryBo> result = userFeignClient.queryUser(money.getUserId());

      if (result.getCode() != 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "调用有误");
      }


      vo.setHeadUrl(result.getData().getWeChatHeadImgUrl());
      vo.setNickName(result.getData().getWeChatNickName());

      return vo;
   }

   /**
    * 分页展示所有答题的排行
    * @param page
    * @param moneyId
    * @return
    */
   @Override
   public Map<String, Object> pageScore(Page<ScoreListVo> page, Long moneyId) {
      Page<ScoreListVo> listVoPage = baseMapper.pageScore(page, moneyId);

      List<ScoreListVo> list = listVoPage.getRecords();

      Map<String, Object> map = new HashMap<>(3);
      map.put("page",listVoPage);
      map.put("allNumber",list.size());

      Money money = moneyService.queryNumber(moneyId);

      map.put("RedEnvelopes",money.getInNumber());

      return map;
   }

   /**
    * 统计收红包的金额或者答题达人
    * @param page
    * @param status
    * @param type
    * @return
    */
   @Override
   public Page<MoneyCountVo> countListMax(Page<MoneyCountVo> page, Integer status, Integer type) {

      if (status != 0 && status != 1) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "梁广兄弟你的status怕是传错了");
      }

      if (type != 0 && type != 1) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "梁广兄弟你的type怕是传错了");
      }

      //当前时间

      String startTime = DateUtils.formatDate(LocalDate.now()) + " 23:59:59";

      //一周前的时间
      LocalDate start = (LocalDate.now()).minusDays(7);

      String endTime = DateUtils.formatDate(start) + " 00:00:00";

      LocalDateTime startDate = DateUtils.parseDateTime(endTime);
      LocalDateTime endDate = DateUtils.parseDateTime(startTime);

      return baseMapper.countListMax(page,status,type,startDate,endDate);
   }
}
