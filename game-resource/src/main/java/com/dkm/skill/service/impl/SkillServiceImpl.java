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
import com.dkm.skill.entity.vo.SkillVo;
import com.dkm.skill.entity.vo.UserSkillVo;
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
   private SkillMapper skillMapper;

   @Autowired
   private UserFeignClient userFeignClient;

   @Override
   public int initSkill(Long userId) {

      LambdaQueryWrapper<Skill> queryWrapper = new LambdaQueryWrapper<>();

      List<Skill> landSeedList = baseMapper.selectList(queryWrapper);

      List<UserSkill> list=new ArrayList<>();

      /**
       * 根据用户id查询所有技能
       * 如果没有则初始化
       */
      int init=0;
      List<SkillUserSkillVo> skillUserSkillVos = baseMapper.queryAllSkillByUserId(userId);
      if(skillUserSkillVos.size()==0){

         for (int i = 0; i < landSeedList.size(); i++) {
            UserSkill userSkill=new UserSkill();
            userSkill.setId(idGenerator.getNumberId());
            userSkill.setUserId(userId);
            userSkill.setSkId(landSeedList.get(i).getId());
            userSkill.setSkGrade(1);
            userSkill.setSkCurrentSuccessRate(100);
            userSkill.setSkAddPrestige(100);
            userSkill.setSkDegreeProficiency(0);
            userSkill.setSkAllConsume(1);
            userSkill.setSkCurrentConsume(0);
            list.add(userSkill);
         }

         init = iUserSkillService.addUserSkill(list);

      }
      return init;
   }

   @Override
   public Map<String,Object> queryAllSkillByUserId() {

      Map<String,Object> map=new HashMap<>(16);

      UserLoginQuery user = localUser.getUser();

      Result<UserInfoQueryBo> userInfoQueryBoResult = userFeignClient.queryUser(user.getId());

      /**
       * 根据用户id查询所有技能
       */
      List<SkillUserSkillVo> skillUserSkillVo = baseMapper.queryAllSkillByUserId(user.getId());

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
   public Map<String,Object> upgradeSkills(Long id,Integer status) {
      Map<String,Object> map=new HashMap<>();

      UserSkill userSkill = iUserSkillService.querySkillById(id);



      if(userSkill.getSkCurrentConsume()<userSkill.getSkAllConsume()){
         throw new ApplicationException(CodeType.SERVICE_ERROR,"金星星数量不足");
      }

      Result<UserInfoQueryBo> userInfoQueryBoResult = userFeignClient.queryUser(localUser.getUser().getId());


      //生成1-100的随机数
      int random = (int) (Math.random() * 100) + 1;

      //成功率百分百升级成功 或者熟练度百分百也百分百升级成功
      if(userSkill.getSkCurrentSuccessRate()==100 || userSkill.getSkDegreeProficiency()==100){
         operation(id, status, userSkill);
         map.put("msg","升级成功");
         return map;
      }


      //另外加百分之10成功率
      if(status==1 && userSkill.getSkCurrentSuccessRate()<100){
         if(userInfoQueryBoResult.getData().getUserInfoGold()<10000){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"金币不足");
         }

         if(random<=userSkill.getSkCurrentSuccessRate()+10){
            operation(id,status,userSkill);
            map.put("msg","升级成功");
            return map;
         }else{

            //升级失败
            UserInfoSkillBo bo = new UserInfoSkillBo();
            bo.setUserId(localUser.getUser().getId());
            bo.setGold(10000);
            bo.setPrestige(0);
            //修改用户信息
            userFeignClient.updateInfo(bo);

            /**
             * 积分加8
             */
            System.out.println("===="+localUser.getUser().getId());
            iIntegralService.updateUserByIntegral(localUser.getUser().getId());

            map.put("msg","升级失败 积分加8");
            return map;
         }
      }

      //另外加百分之20成功率
      if(status==2 && userSkill.getSkCurrentSuccessRate()<100){
         System.out.println("钻石");
         if(userInfoQueryBoResult.getData().getUserInfoDiamonds()<20){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"钻石不足");
         }

         if(random<=userSkill.getSkCurrentSuccessRate()+20){
            operation(id,status,userSkill);
            map.put("msg","升级成功");
            return map;
         }else{
            //升级失败
            UserInfoSkillBo bo = new UserInfoSkillBo();
            bo.setUserId(localUser.getUser().getId());
            bo.setDiamonds(20);
            bo.setPrestige(0);
            //修改用户信息
            userFeignClient.updateInfo(bo);

            /**
             * 积分加8
             */
            iIntegralService.updateUserByIntegral(localUser.getUser().getId());
            map.put("msg","升级失败 积分加8");
            return map;
         }
      }

          return map;
   }

   @Override
   public List<UserSkillVo> querySkillByUserId(Long userId) {
      return skillMapper.querySkillByUserId(userId);
   }

   @Override
   public List<SkillVo> queryAllSkillByUserIdImgGrade(Long userId) {
      return skillMapper.queryAllSkillByUserIdImgGrade(userId);
   }




   public Integer operation(Long id,Integer status,UserSkill userSkill){

      UserInfoSkillBo bo=new UserInfoSkillBo();
      //增加声望
      bo.setPrestige(userSkill.getSkAddPrestige());

      /**
       * 手续费为1是消耗10000金币
       */
      if(status==1){
         System.out.println("消耗10000金币");
         bo.setGold(10000);
         bo.setUserId(localUser.getUser().getId());
         Result result = userFeignClient.updateInfo(bo);
         if(result.getCode()!=0){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"用户信息修改异常");
         }
      }
      /**
       * 手续费为2是消耗20钻石
       */
      if(status==2){
         bo.setUserId(localUser.getUser().getId());
         bo.setDiamonds(20);
         Result result = userFeignClient.updateInfo(bo);
         if(result.getCode()!=0){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"用户信息修改异常");
         }
      }



      //等级加一
      userSkill.setSkGrade(userSkill.getSkGrade()+1);

      if (userSkill.getSkGrade() >= 2 && userSkill.getSkGrade() < 10) {
         userSkill.setSkCurrentSuccessRate(80);
      } else if (userSkill.getSkGrade() >= 10 && userSkill.getSkGrade() < 20) {
         userSkill.setSkCurrentSuccessRate(60);
         userSkill.setSkAllConsume(6);
      } else if (userSkill.getSkGrade() >= 20 && userSkill.getSkGrade() < 30) {
         userSkill.setSkCurrentSuccessRate(40);
         userSkill.setSkAllConsume(8);
      } else if (userSkill.getSkGrade() >= 30 && userSkill.getSkGrade() < 40) {
         userSkill.setSkCurrentSuccessRate(20);
         userSkill.setSkAllConsume(12);
      } else {
         userSkill.setSkCurrentSuccessRate(10);
         userSkill.setSkAllConsume(15);
      }


      userSkill.setSkCurrentConsume(userSkill.getSkCurrentConsume()-userSkill.getSkAllConsume());

      //数量度加10
      userSkill.setSkDegreeProficiency(userSkill.getSkDegreeProficiency()+10);

      if(userSkill.getSkDegreeProficiency()==100){
         userSkill.setSkDegreeProficiency(0);
      }

      //算出下一等级的声望
      int anInt = Integer.parseInt(String.format("%.0f", Math.pow(userSkill.getSkGrade(), 2 / 5.0) * 100));
      userSkill.setSkAddPrestige(anInt);

      //修改用户技能信息
      int i = iUserSkillService.updateUserSkill(id, userSkill);
      if(i<=0){
         throw new ApplicationException(CodeType.SERVICE_ERROR,"修改用户技能信息失败");
      }
      return i;
   }

}
