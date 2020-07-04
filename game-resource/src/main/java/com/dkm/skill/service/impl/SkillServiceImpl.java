package com.dkm.skill.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.skill.dao.SkillMapper;
import com.dkm.skill.entity.Skill;
import com.dkm.skill.service.ISkillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author qf
 * @date 2020/5/27
 * @vesion 1.0
 **/
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class SkillServiceImpl extends ServiceImpl<SkillMapper, Skill> implements ISkillService {


}
