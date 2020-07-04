package com.dkm.skill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.constanct.CodeType;
import com.dkm.entity.bo.SkillBo;
import com.dkm.exception.ApplicationException;
import com.dkm.skill.dao.SkillMapper;
import com.dkm.meskill.entity.Skill;
import com.dkm.skill.entity.vo.SkillInsertVo;
import com.dkm.skill.service.ISkillService;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

   @Override
   public void insertSkill(SkillInsertVo vo) {
      Skill skill = new Skill();

      BeanUtils.copyProperties(vo, skill);

      skill.setId(idGenerator.getNumberId());

      int insert = baseMapper.insert(skill);

      if (insert <= 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "添加失败");
      }
   }

   @Override
   public Skill queryById(Long skillId) {
      return baseMapper.selectById(skillId);
   }

   @Override
   public List<Skill> listAllSkill() {
      return baseMapper.selectList(null);
   }


   @Override
   public List<SkillBo> queryAllSkillByUserId(Long userId) {
      return baseMapper.queryAllSkillByUserId (userId);
   }
}
