package com.dkm.skill.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.feign.UserFeignClient;
import com.dkm.integral.service.IIntegralService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.seed.entity.LandSeed;
import com.dkm.skill.dao.SkillMapper;
import com.dkm.skill.entity.Skill;
import com.dkm.skill.entity.UserSkill;
import com.dkm.skill.entity.vo.SkillUserSkillVo;
import com.dkm.skill.service.ISkillService;
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
 * @author qf
 * @date 2020/5/27
 * @vesion 1.0
 **/
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SkillServiceImpl extends ServiceImpl<SkillMapper, Skill> implements ISkillService {

   @Autowired
   private IdGenerator idGenerator;

   @Autowired
   private LocalUser localUser;

   @Autowired
   private IUserSkillService iUserSkillService;

   @Autowired
   private IIntegralService iIntegralService;

   @Autowired
   private UserFeignClient userFeignClient;

   @Override
   public Map<String,Object> queryAllSkillByUserId() {

      Map<String,Object> map=new HashMap<>(16);

      UserLoginQuery user = localUser.getUser();

      LambdaQueryWrapper<Skill> queryWrapper = new LambdaQueryWrapper<>();

      List<Skill> landSeedList = baseMapper.selectList(queryWrapper);

      List<UserSkill> list=new ArrayList<>();

      Result<UserInfoQueryBo> userInfoQueryBoResult = userFeignClient.queryUser(user.getId());



      /**
       * 根据用户id查询所有技能
       */
      List<SkillUserSkillVo> skillUserSkillVos = baseMapper.queryAllSkillByUserId(user.getId());
      if(skillUserSkillVos.size()==0){

         for (int i = 0; i < landSeedList.size(); i++) {
            UserSkill userSkill=new UserSkill();
            userSkill.setId(idGenerator.getNumberId());
            userSkill.setUserId(user.getId());
            userSkill.setSkId(landSeedList.get(i).getId());
            userSkill.setSkGrade(1);
            userSkill.setSkCurrentSuccessRate(100);
            userSkill.setSkAddPrestige(100);
            userSkill.setSkDegreeProficiency(0);
            userSkill.setSkAllConsume(1);
            userSkill.setSkCurrentConsume(0);
            list.add(userSkill);
         }

         iUserSkillService.addUserSkill(list);

      }
      List<SkillUserSkillVo> skillUserSkillVo = baseMapper.queryAllSkillByUserId(user.getId());
      for (int i = 0; i < skillUserSkillVo.size(); i++) {

      }


      map.put("skillUserSkillVo",skillUserSkillVo);
      map.put("gold",10000);
      map.put("Diamonds",20);
      //积分
      map.put("integral",iIntegralService.queryUserIdIntegral());
      //用户金币数量
      map.put("userGold",userInfoQueryBoResult.getData().getUserInfoGold());

      //用户钻石数量
      map.put("UserDiamonds",userInfoQueryBoResult.getData().getUserInfoDiamonds());

      return map;
   }
}
