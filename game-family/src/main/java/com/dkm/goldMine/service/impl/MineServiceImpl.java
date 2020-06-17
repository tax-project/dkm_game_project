package com.dkm.goldMine.service.impl;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.family.dao.FamilyDao;
import com.dkm.family.service.FamilyService;
import com.dkm.goldMine.bean.vo.FamilyGoldMineVo;
import com.dkm.goldMine.dao.MineMapper;
import com.dkm.goldMine.service.IMineService;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class MineServiceImpl implements IMineService {

    @Resource
    private MineMapper mineMapper;
    @Resource
    private FamilyDao familyDao;


    @Override
    public FamilyGoldMineVo getFamilyGoldMine(Long familyIdInt) {
        if (familyDao.selectById(familyIdInt) == null){
            // family  not found
            throw new ApplicationException(CodeType.PARAMETER_ERROR,"家族 id [" + familyIdInt + "] 不存在！");
        }
        if (mineMapper.selectMineByFamilyId(familyIdInt) == null){
            log.info("NULL");
        }else {
            log.info("FIND THERE");
        }
        return null;
    }
}
