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
import com.dkm.knapsack.domain.bo.IncreaseUserInfoBO;
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
            int anInt = Integer.parseInt(String.format("%.0f", Math.pow(skillUserSkillVo.get(i).getSkGrade(), 2 / 5.0) * 1000));
            /*//当前声望累计达到总声望
            skillUserSkillVo.get(i).setSkAllPrestige(anInt + skill.getSkAllPrestige());*/
            //升级下一级增加的声望
            skillUserSkillVo.get(i).setSkAddPrestige(anInt);
            if(skillUserSkillVo.get(i).getSkGrade()>=2 && skillUserSkillVo.get(i).getSkGrade()<10){
               skillUserSkillVo.get(i).setSkCurrentSuccessRate(80);
            }else if(skillUserSkillVo.get(i).getSkGrade()>=10 && skillUserSkillVo.get(i).getSkGrade()< 20){
               skillUserSkillVo.get(i).setSkCurrentSuccessRate(60);
               skillUserSkillVo.get(i).setSkAllConsume(6);
            }else if (skillUserSkillVo.get(i).getSkGrade() >= 20 && skillUserSkillVo.get(i).getSkGrade() < 30) {
               skillUserSkillVo.get(i).setSkCurrentSuccessRate(40);
               skillUserSkillVo.get(i).setSkAllConsume(8);
            }else if (skillUserSkillVo.get(i).getSkGrade() >= 30 && skillUserSkillVo.get(i).getSkGrade() < 40) {
               skillUserSkillVo.get(i).setSkCurrentSuccessRate(20);
               skillUserSkillVo.get(i).setSkAllConsume(12);
            }else {
               skillUserSkillVo.get(i).setSkCurrentSuccessRate(10);
               skillUserSkillVo.get(i).setSkAllConsume(12);
            }


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



   @Override
   public int upgradeSkills(Long id,Integer status) {
      UserSkill userSkill = iUserSkillService.querySkillById(id);
      int anInt = Integer.parseInt(String.format("%.0f", Math.pow(userSkill.getSkGrade(), 2 / 5.0) * 1000));

      if(userSkill.getSkCurrentConsume()<userSkill.getSkAllConsume()){
         throw new ApplicationException(CodeType.SERVICE_ERROR,"金星星数量不足");
      }

      if(userSkill.getSkCurrentSuccessRate()==100){
         //手续费为1是消耗10000金币
         if(status==1){
            IncreaseUserInfoBO increaseUserInfoBO=new IncreaseUserInfoBO();
            increaseUserInfoBO.setUserInfoGold(10000);
            increaseUserInfoBO.setUserId(localUser.getUser().getId());
            userFeignClient.cutUserInfo(increaseUserInfoBO);
         }
         //手续费为2是消耗20钻石
         if(status==2){
            IncreaseUserInfoBO increaseUserInfoBO=new IncreaseUserInfoBO();
            increaseUserInfoBO.setUserId(localUser.getUser().getId());
            increaseUserInfoBO.setUserInfoDiamonds(20);
            userFeignClient.cutUserInfo(increaseUserInfoBO);
         }

         //增加声望
         UserInfoSkillBo userInfoSkillBo=new UserInfoSkillBo();
         userInfoSkillBo.setPrestige(userSkill.getSkAddPrestige());
         userFeignClient.updateInfo(userInfoSkillBo);

         userSkill.setSkGrade(userSkill.getSkGrade()+1);

         userSkill.setSkCurrentConsume(userSkill.getSkCurrentConsume()-userSkill.getSkAllConsume());
      }




      return 0;
   }


}
