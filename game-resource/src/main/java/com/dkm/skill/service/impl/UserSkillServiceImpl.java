package com.dkm.skill.service.impl;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.skill.dao.UserSkillMapper;
import com.dkm.skill.entity.UserSkill;


import com.dkm.skill.service.IUserSkillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author qf
 * @date 2020/5/27
 * @vesion 1.0
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserSkillServiceImpl extends ServiceImpl<UserSkillMapper, UserSkill> implements IUserSkillService {


}
