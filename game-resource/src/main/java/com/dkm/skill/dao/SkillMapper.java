package com.dkm.skill.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.skill.entity.Skill;
import com.dkm.skill.entity.vo.SkillUserSkillVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/27
 * @vesion 1.0
 **/
@Component
public interface SkillMapper extends IBaseMapper<Skill> {

   /**
    * 根据用户id查询所有技能
    * @param userId
    * @return
    */
   List<SkillUserSkillVo> queryAllSkillByUserId(Long userId);


}
