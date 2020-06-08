package com.dkm.problem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.config.RedisConfig;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.UserFeignClient;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.problem.dao.ProblemMapper;
import com.dkm.problem.entity.Money;
import com.dkm.problem.entity.Problem;
import com.dkm.problem.entity.vo.ProblemVo;
import com.dkm.problem.service.IMoneyService;
import com.dkm.problem.service.IProblemService;
import com.dkm.utils.DateUtils;
import com.dkm.utils.IdGenerator;
import com.dkm.utils.StringUtils;
import com.dkm.vilidata.RandomData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author qf
 * @date 2020/5/8
 * @vesion 1.0
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper,Problem> implements IProblemService {

   @Autowired
   private IdGenerator idGenerator;

   @Autowired
   private RandomData randomData;

   @Autowired
   private IMoneyService moneyService;

   @Autowired
   private RedisConfig redisConfig;

   @Autowired
   private UserFeignClient userFeignClient;

   @Autowired
   private LocalUser localUser;

   private String redisLock = "RedisLock::problem";

   /**
    * 批量增加问题
    * @param list
    */
   @Override
   public void insertAllProblem(List<ProblemVo> list) {

      for (ProblemVo vo : list) {
         vo.setId(idGenerator.getNumberId());
      }

      Integer integer = baseMapper.insertAllProblem(list);

      if (integer <= 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "批量增加失败");
      }
   }


   /**
    * 随机返回10条数据
    * @return
    */
   @Override
   public List<Problem> listProblem(Long id) {

      UserLoginQuery user = localUser.getUser();

      try {
         Boolean lock = redisConfig.redisLock(redisLock);

         if (!lock) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "网络拥挤");
         }

         //拿到分布式锁
         //先判断该用户是否达到今日红包的上限
         Result<UserInfoQueryBo> result = userFeignClient.queryUser(user.getId());
         if (result.getCode() != 0) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, result.getMsg());
         }
         UserInfoQueryBo data = result.getData();
         if (data == null) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "参数查询有误");
         }

         if (StringUtils.isBlank(data.getUserInfoEnvelopeQueryTime()) || !DateUtils.formatDate(LocalDate.now()).equals(data.getUserInfoEnvelopeQueryTime())) {
            //今天还没有抢过红包或者从来没抢过红包
            Integer integer = baseMapper.updateMuch(LocalDate.now(), user.getId());

            if (integer <= 0) {
               throw new ApplicationException(CodeType.SERVICE_ERROR, "次数修改失败");
            }
         } else {
            //今天抢过红包了
            //先判断今天的次数有没有超标
            if (data.getUserInfoEnvelopeMuch() >= data.getUserInfoAllEnvelopeMuch()) {
               //达到次数上限了
               throw new ApplicationException(CodeType.SERVICE_ERROR, "您今日抢红包次数已达上限,您可以通过发红包或者明日再来");
            }

            Integer integer = baseMapper.updateMuch(null, user.getId());

            if (integer <= 0) {
               throw new ApplicationException(CodeType.SERVICE_ERROR, "次数修改失败");
            }

         }

         //查询人数是否达到用户设定的上限
         Money money = moneyService.queryNumber(id);

         if (money == null) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "传的id有误");
         }

         if (money.getInNumber() >= money.getNumber()) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "红包答题人数已达上限");
         }

         //修改红包状态
         moneyService.updateMoneyStatus(id,1);
      }  finally {
         redisConfig.deleteLock(redisLock);
      }


      //查询所有，随机抽取10条记录
      List<Problem> list = baseMapper.selectList(null);
      if (list.size() <= 10) {
         return list;
      }

      //随机得到10个随机数
      Set<Integer> set = randomData.getList(list.size(), 10);

      Set<Problem> result = new HashSet<>();
      for (int i = 0; i <= list.size()-1; i++) {
         for (Integer integer : set) {
            result.add(list.get(integer));
         }
      }

      //将set转List
      List<Problem> resultList = new ArrayList<>();
      resultList.addAll(result);

      return resultList;
   }


}
