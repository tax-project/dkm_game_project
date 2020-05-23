package com.dkm.family.service.impl;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.family.dao.FamilyDao;
import com.dkm.family.dao.FamilyDetailDao;
import com.dkm.family.entity.FamilyDetailEntity;
import com.dkm.family.entity.FamilyEntity;
import com.dkm.family.service.FamilyService;
import com.dkm.utils.IdGenerator;
import com.dkm.utils.QrCodeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @program: game_project
 * @description: fimaly
 * @author: zhd
 * @create: 2020-05-20 10:59
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class FamilyServiceImpl implements FamilyService {
    @Resource
    private FamilyDao familyDao;
    @Resource
    private FamilyDetailDao familyDetailDao;
    @Resource
    private IdGenerator idGenerator;
    @Value("${family.url}")
    private String url;
    @Override
    public void creatFamily(Long userId,String name) {
        FamilyEntity familyEntity = new FamilyEntity();
        familyEntity.setFamilyCreateTime(LocalDateTime.now());
        familyEntity.setFamilyId(idGenerator.getNumberId());
        familyEntity.setFamilyGrade(1);
        familyEntity.setFamilyQrcode(url+"/family/showCode?familyId="+familyEntity.getFamilyId());
        familyEntity.setFamilyName(name);
        FamilyDetailEntity familyDetailEntity = new FamilyDetailEntity();
        familyDetailEntity.setIsAdmin(2);
        familyDetailEntity.setUserId(userId);
        familyDetailEntity.setFamilyDetailsId(idGenerator.getNumberId());
        familyDetailEntity.setFamilyId(familyEntity.getFamilyId());
        if(familyDao.insert(familyEntity)<1||familyDetailDao.insert(familyDetailEntity)<1){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"创建家族失败");
        }
    }

    @Override
    public void getHotFamily() {

    }
}
