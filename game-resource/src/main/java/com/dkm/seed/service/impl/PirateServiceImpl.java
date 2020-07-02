package com.dkm.seed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.seed.dao.PirateMapper;
import com.dkm.seed.entity.Pirate;
import com.dkm.seed.service.IPirateService;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author MQ
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/7/1 16:20
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class PirateServiceImpl extends ServiceImpl<PirateMapper, Pirate> implements IPirateService {

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private LocalUser localUser;

    @Override
    public int addPirate(Pirate pirate) {
        pirate.setPirateId(idGenerator.getNumberId());
        //被盗时间
        pirate.setPirateLossTime(LocalDateTime.now());

        int insert = baseMapper.insert(pirate);

        if(insert<=0){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"添加被盗数据异常");
        }
        return insert;
    }
}
