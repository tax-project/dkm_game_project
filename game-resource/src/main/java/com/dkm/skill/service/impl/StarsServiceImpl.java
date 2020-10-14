package com.dkm.skill.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.entity.bo.UserInfoSkillBo;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.UserFeignClient;
import com.dkm.integral.service.IIntegralService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.skill.dao.SkillMapper;
import com.dkm.skill.dao.StarsMapper;
import com.dkm.skill.entity.Skill;
import com.dkm.skill.entity.Stars;
import com.dkm.skill.entity.UserSkill;
import com.dkm.skill.entity.vo.SkillUserSkillVo;
import com.dkm.skill.entity.vo.SkillVo;
import com.dkm.skill.entity.vo.UserSkillVo;
import com.dkm.skill.service.ISkillService;
import com.dkm.skill.service.IStarsService;
import com.dkm.skill.service.IUserSkillService;
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
 * @author MQ
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/11 16:17
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class StarsServiceImpl extends ServiceImpl<StarsMapper, Stars> implements IStarsService {

   @Autowired
   private StarsMapper starsMapper;

   @Override
   public Stars queryCurrentConsumeByUserId(Long userId) {
      //查询信息返回
      return starsMapper.queryCurrentConsumeByUserId(userId);
   }

   @Override
   public int addUserVenusNum(Long userId) {
      Stars stars=new Stars();
      stars.setUserId(userId);
      stars.setSkCurrentConsume(0);
      //添加
      return baseMapper.insert(stars);
   }

   @Override
   public int updateUserVenusNum(Stars stars) {
      LambdaQueryWrapper<Stars> queryWrapper = new LambdaQueryWrapper<Stars>()
              .eq(Stars::getUserId, stars.getUserId());
      //修改
      return baseMapper.update(stars,queryWrapper);
   }
}
