package com.dkm.userInfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.constanct.CodeType;
import com.dkm.entity.bo.UserInfoSkillBo;
import com.dkm.entity.bo.UserPlunderBo;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.userInfo.dao.UserInfoMapper;
import com.dkm.userInfo.entity.UserInfo;
import com.dkm.userInfo.entity.bo.IncreaseUserInfoBO;
import com.dkm.userInfo.service.IUserInfoService;
import com.dkm.utils.IdGenerator;
import com.dkm.vilidata.RandomData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/11
 * @vesion 1.0
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

   @Autowired
   private IdGenerator idGenerator;

   @Override
   public void insertUserInfo(Long userId) {

      UserInfo userInfo = new UserInfo();

      userInfo.setUserInfoId(idGenerator.getNumberId());
      userInfo.setUserId(userId);

      //初始化用户信息
      userInfo.setUserInfoGrade(1);
      userInfo.setUserInfoGold(10000);
      userInfo.setUserInfoRenown(500);
      userInfo.setUserInfoDiamonds(0);
      userInfo.setUserInfoIsVip(0);
      userInfo.setUserInfoIntegral(0);
      userInfo.setUserInfoStrength(200);
      userInfo.setUserInfoAllStrength(200);
      userInfo.setUserInfoNowExperience(0L);
      userInfo.setUserInfoNextExperience(600L);
      userInfo.setUserInfoPacketBalance(0.0);
      userInfo.setUserInfoEnvelopeMuch(0);
      userInfo.setUserInfoAllEnvelopeMuch(15);

      int insert = baseMapper.insert(userInfo);

      if (insert <= 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "增加失败");
      }
   }


   /**
    * 修改红包总次数
    * @param much 次数
    * @param userId 用户id
    */
   @Override
   public void updateUserInfo(Integer much, Long userId) {

      LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<UserInfo>()
            .eq(UserInfo::getUserId,userId);

      UserInfo userInfo = baseMapper.selectOne(wrapper);

      if (userInfo == null) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "查询错误");
      }

      UserInfo userInfo1 = new UserInfo();

      userInfo1.setUserInfoAllEnvelopeMuch(userInfo.getUserInfoAllEnvelopeMuch() + much);

      baseMapper.update(userInfo1, wrapper);
   }

   @Override
   public void increaseUserInfo(IncreaseUserInfoBO increaseUserInfoBO) {
      Integer integer = baseMapper.increaseUserInfo(increaseUserInfoBO);
      if (integer<=0){
         throw new ApplicationException(CodeType.SERVICE_ERROR, "添加失败");
      }
   }

   @Override
   public void cutUserInfo(IncreaseUserInfoBO increaseUserInfoBO) {
      Integer integer = baseMapper.cutUserInfo(increaseUserInfoBO);
      if (integer<=0){
         throw new ApplicationException(CodeType.SERVICE_ERROR, "减少失败");
      }
   }



   @Override
   public List<UserPlunderBo> listUserPlunder() {
      return baseMapper.listUserPlunder ();
   }

   @Override
   public void updateStrength(Long userId, Integer grade) {
      Integer integer = baseMapper.updateStrength(userId, grade);

      if (integer <= 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR);
      }
   }

   @Override
   public void updateInfo(UserInfoSkillBo bo) {
      Integer integer = baseMapper.updateInfo(bo);

      if (integer <= 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR);
      }
   }
}
