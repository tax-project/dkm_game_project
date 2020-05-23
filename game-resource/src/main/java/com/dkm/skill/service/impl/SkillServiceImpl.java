package com.dkm.skill.service.impl;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.skill.dao.SkillMapper;
import com.dkm.skill.entity.Skill;
import com.dkm.skill.entity.Stars;
import com.dkm.skill.entity.UserSkill;
import com.dkm.skill.entity.vo.MySkillVo;
import com.dkm.skill.service.ISkillService;
import com.dkm.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/5/22 18:41
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SkillServiceImpl implements ISkillService {

    @Autowired
    private SkillMapper skillMapper;

   @Autowired
   private LocalUser localUser;

    @Autowired
    private IdGenerator idGenerator;

    /**
     * 查询我的技能
     * @return
     */
    @Override
    public List<MySkillVo> queryMySkill() {
        List<MySkillVo> mySkillVos = skillMapper.queryMySkill(localUser.getUser().getId());
        if(mySkillVos.size()==0){
            //给技能表初始化
            UserSkill userSkill=new UserSkill();
            //技能
            List<UserSkill> list=new ArrayList<>();
            //星星
            List<Stars> starsList=new ArrayList<>();
            List<Skill> skills = skillMapper.selectList(null);
            for (int i = 1; i <skills.size(); i++) {
                userSkill.setUserId(localUser.getUser().getId());
                userSkill.setSkId(skills.get(i).getSkId());
                userSkill.setSkCurrentSuccessRate(60);
                userSkill.setSkDegreeProficiency("1");
                userSkill.setSkGrad(1);
                list.add(userSkill);
            }
            for (int i = 0; i < 2; i++) {
                Stars stars=new Stars();
                stars.setSId(idGenerator.getNumberId());
                stars.setSId(localUser.getUser().getId());
                stars.setSStar(i);
                stars.setSTotalConsumedQuantity(2);
                stars.setSCurrentlyHasNum(0);
                starsList.add(stars);
            }
            int i = skillMapper.addSkill(list);
            if(i<=0){
                throw new ApplicationException(CodeType.PARAMETER_ERROR,"初始化技能异常");
            }
            int i1 = skillMapper.addStarts(starsList);
            if(i1<=0){
                throw new ApplicationException(CodeType.PARAMETER_ERROR,"初始化星星异常");
            }
        }
            return mySkillVos;
    }


}
