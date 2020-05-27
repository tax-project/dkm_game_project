package com.dkm.family.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.family.dao.FamilyDao;
import com.dkm.family.dao.FamilyDetailDao;
import com.dkm.family.entity.FamilyDetailEntity;
import com.dkm.family.entity.FamilyEntity;
import com.dkm.family.service.FamilyService;
import com.dkm.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
    public void creatFamily(Long userId,FamilyEntity family) {
        if(familyDetailDao.selectOne(new QueryWrapper<FamilyDetailEntity>().lambda().eq(FamilyDetailEntity::getUserId,userId))!=null){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"请先退出当前家族");
        }
        family.setFamilyCreateTime(LocalDateTime.now());
        family.setFamilyId(idGenerator.getNumberId());
        family.setFamilyGrade(1);
        family.setFamilyJoin(0);
        family.setFamilyQrcode(url+"/family/showCode?familyId="+family.getFamilyId());
        FamilyDetailEntity familyDetailEntity = new FamilyDetailEntity();
        familyDetailEntity.setIsAdmin(2);
        familyDetailEntity.setUserId(userId);
        familyDetailEntity.setFamilyDetailsId(idGenerator.getNumberId());
        familyDetailEntity.setFamilyId(family.getFamilyId());
        if(familyDao.insert(family)<1||familyDetailDao.insert(familyDetailEntity)<1){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"创建家族失败");
        }
    }

    @Override
    public Map<String,Object> getFamilyInfo(Long familyId) {
        Map<String,Object> map = new HashMap<>(2);
        map.put("family",familyDao.selectById(familyId));
        map.put("user",familyDetailDao.selectList(new QueryWrapper<FamilyDetailEntity>().lambda().eq(FamilyDetailEntity::getFamilyId,familyId)));
        return map;
    }

    @Override
    public FamilyEntity getMyFamily(Long userId) {
        FamilyDetailEntity familyDetailEntity = familyDetailDao.selectOne(new QueryWrapper<FamilyDetailEntity>().lambda().eq(FamilyDetailEntity::getUserId, userId));
        if(familyDetailEntity==null){
            throw  new ApplicationException(CodeType.RESOURCES_NOT_FIND,"还未加入家族");
        }
        return familyDao.selectOne(new QueryWrapper<FamilyEntity>().lambda().eq(FamilyEntity::getFamilyId, familyDetailEntity.getFamilyId()));
    }

    @Override
    public void exitFamily(Long userId) {
        if(familyDetailDao.delete(new QueryWrapper<FamilyDetailEntity>().lambda().eq(FamilyDetailEntity::getUserId, userId))<1){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"退出家族失败");
        }
    }



    @Override
    public void getHotFamily() {

    }
}
