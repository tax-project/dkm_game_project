package com.dkm.skill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.config.RedisConfig;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoSkillBo;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.UserFeignClient;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.skill.dao.UserSkillMapper;
import com.dkm.skill.entity.Skill;
import com.dkm.skill.entity.UserSkill;
import com.dkm.skill.entity.vo.UserSkillResultVo;
import com.dkm.skill.entity.vo.UserSkillUpGradeVo;
import com.dkm.skill.entity.vo.UserSkillVo;
import com.dkm.skill.service.ISkillService;
import com.dkm.skill.service.IUserSkillService;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author qf
 * @date 2020/5/27
 * @vesion 1.0
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserSkillServiceImpl extends ServiceImpl<UserSkillMapper, UserSkill> implements IUserSkillService {

   @Autowired
   private IdGenerator idGenerator;

   @Autowired
   private LocalUser localUser;

   @Autowired
   private UserFeignClient userFeignClient;

   @Autowired
   private RedisConfig redisConfig;

   @Autowired
   private ISkillService skillService;

   private String redisLock = "REDIS::LOCK:SKILL";

   @Override
   public void insertUserSkill(UserSkillVo vo) {
      UserSkill userSkill = new UserSkill();

      userSkill.setId(idGenerator.getNumberId());

      userSkill.setSkId(vo.getSkId());

      UserLoginQuery user = localUser.getUser();

      userSkill.setUserId(user.getId());
      userSkill.setSkGrade(1);
      userSkill.setSkCurrentSuccessRate(8);
      //当前声望累计达到总声望
      userSkill.setSkAllPrestige(1000);
      //升级一级增加的声望
      userSkill.setAkAddPrestige(319);
      //消耗的总个数
      userSkill.setSkAllConsume(2);
      //当前使用消耗的个数
      userSkill.setSkCurrentConsume(0);

      int insert = baseMapper.insert(userSkill);

      if (insert <= 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "初始化失败");
      }

      //将用户的声望增加1000
      UserInfoSkillBo bo = new UserInfoSkillBo();
      bo.setUserId(user.getId());
      bo.setGold(0);
      bo.setPrestige(1000);
      //修改用户信息
      userFeignClient.updateInfo(bo);
   }

   /**
    * 升级
    * @param vo id
    */
   @Override
   public void upGrade(UserSkillUpGradeVo vo) {

      UserLoginQuery user = localUser.getUser();

      try {
         //获得分布式锁
         Boolean lock = redisConfig.redisLock(redisLock);

         if (!lock) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "网络拥挤,请稍后再试");
         }

         UserSkill skill = baseMapper.selectById(vo.getId());

         if (skill == null) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "参数id有误");
         }

         //生成1-10的随机数
         int random = (int) (Math.random() * 10) + 1;

         UserSkill userSkill = new UserSkill();
         userSkill.setId(vo.getId());

         if (random > skill.getSkCurrentSuccessRate()) {
            //升级失败
            throw new ApplicationException(CodeType.SERVICE_ERROR, "很可惜，差一点点就升级成功了");
         }

         //升级成功
         userSkill.setSkGrade(skill.getSkGrade() + 1);
         if (skill.getSkGrade() >= 0 && skill.getSkGrade() < 10) {
            userSkill.setSkCurrentSuccessRate(8);
         } else if (skill.getSkGrade() >= 10 && skill.getSkGrade() < 20) {
            userSkill.setSkCurrentSuccessRate(6);
            userSkill.setSkAllConsume(2);
            userSkill.setSkCurrentConsume(0);
         } else if (skill.getSkGrade() >= 20 && skill.getSkGrade() < 30) {
            userSkill.setSkCurrentSuccessRate(4);
            userSkill.setSkAllConsume(2);
            userSkill.setSkCurrentConsume(0);
         } else if (skill.getSkGrade() >= 30 && skill.getSkGrade() < 40) {
            userSkill.setSkCurrentSuccessRate(2);
            userSkill.setSkAllConsume(2);
            userSkill.setSkCurrentConsume(0);
         } else {
            userSkill.setSkCurrentSuccessRate(1);
            userSkill.setSkAllConsume(2);
            userSkill.setSkCurrentConsume(0);
         }

         //算出下一等级的声望
         int anInt = Integer.parseInt(String.format("%.0f", Math.pow(2, 2 / 5.0) * 1000));

         //当前声望累计达到总声望
         userSkill.setSkAllPrestige(anInt);
         //升级下一级增加的声望
         userSkill.setAkAddPrestige(anInt - skill.getSkAllPrestige());

         int updateById = baseMapper.updateById(userSkill);

         if (updateById <= 0) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "升级失败");
         }

         //修改用户消耗的1000金币  以及增加的声望
         UserInfoSkillBo bo = new UserInfoSkillBo();
         bo.setUserId(user.getId());
         bo.setGold(1000);
         bo.setPrestige(skill.getAkAddPrestige());
         //修改用户信息
         userFeignClient.updateInfo(bo);
      } finally {
         redisConfig.deleteLock(redisLock);
      }

   }

   @Override
   public void consume(Long id) {

      UserSkill userSkill = baseMapper.selectById(id);

      if (userSkill == null) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "参数id有误");
      }

      if (userSkill.getSkCurrentConsume() >= userSkill.getSkAllConsume()) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "消耗次数已达上限,您可以充值增加消耗次数");
      }

      UserSkill skill = new UserSkill();
      skill.setId(id);

      if (userSkill.getSkCurrentSuccessRate() < 10) {
         skill.setSkCurrentSuccessRate(userSkill.getSkCurrentSuccessRate() + 1);
      }

      skill.setSkCurrentConsume(userSkill.getSkCurrentConsume() + 1);

      int updateById = baseMapper.updateById(skill);

      if (updateById <= 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "操作失败");
      }
   }

   @Override
   public UserSkillResultVo getSkillResult(Long skillId) {
      UserLoginQuery user = localUser.getUser();

      Skill skill = skillService.queryById(skillId);

      UserSkillResultVo vo = new UserSkillResultVo();
      vo.setSkName(skill.getSkName());
      vo.setSkImg(skill.getSkImg());

      //查询技能详情表
      LambdaQueryWrapper<UserSkill> wrapper = new LambdaQueryWrapper<UserSkill>()
            .eq(UserSkill::getUserId,user.getId())
            .eq(UserSkill::getSkId,skillId);

      UserSkill userSkill = baseMapper.selectOne(wrapper);

      vo.setId(userSkill.getId());
      vo.setAkAddPrestige(userSkill.getAkAddPrestige());
      vo.setSkAllConsume(userSkill.getSkAllConsume());
      vo.setSkAllPrestige(userSkill.getSkAllPrestige());
      vo.setSkCurrentConsume(userSkill.getSkCurrentConsume());
      vo.setSkGrade(userSkill.getSkGrade());

      String success = String.valueOf(userSkill.getSkCurrentSuccessRate());

      String newSuccess = success + "0%";
      vo.setSkCurrentSuccessRate(newSuccess);

      return vo;
   }
}
