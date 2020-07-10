package com.dkm.skill.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.skill.dao.UserSkillMapper;
import com.dkm.skill.entity.UserSkill;


import com.dkm.skill.service.IUserSkillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author MQ
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/11 16:17
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserSkillServiceImpl extends ServiceImpl<UserSkillMapper, UserSkill> implements IUserSkillService {


   @Override
   public int addUserSkill(List<UserSkill> list) {
      return baseMapper.addUserSkill(list);
   }

   @Override
   public UserSkill querySkillById(Long id) {
      return baseMapper.selectById(id);
   }

   @Override
   public int updateUserSkill(Long id,UserSkill userSkill) {
      LambdaQueryWrapper<UserSkill> queryWrapper = new LambdaQueryWrapper<UserSkill>()
              .eq(UserSkill::getId, id);
      return baseMapper.update(userSkill,queryWrapper);
   }


}
