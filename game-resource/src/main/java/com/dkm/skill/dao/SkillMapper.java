package com.dkm.skill.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.entity.bo.SkillBo;
import com.dkm.meskill.entity.Skill;
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
    *  根据用户Id查询所有技能
    * @param userId 用户Id
    * @return 返回技能图片和等级
    */
   List<SkillBo> queryAllSkillByUserId(Long userId);


}
