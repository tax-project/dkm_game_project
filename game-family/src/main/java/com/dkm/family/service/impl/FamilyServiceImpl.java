package com.dkm.family.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.family.dao.FamilyDao;
import com.dkm.family.dao.FamilyDetailDao;
import com.dkm.family.entity.FamilyDetailEntity;
import com.dkm.family.entity.FamilyEntity;
import com.dkm.family.entity.vo.FamilyUsersVo;
import com.dkm.family.entity.vo.HotFamilyVo;
import com.dkm.family.service.FamilyService;
import com.dkm.utils.IdGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
    @Override
    public void creatFamily(Long userId,FamilyEntity family) {
        if(familyDetailDao.selectOne(new QueryWrapper<FamilyDetailEntity>().lambda().eq(FamilyDetailEntity::getUserId,userId))!=null){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"请先退出当前家族");
        }
        family.setFamilyCreateTime(LocalDateTime.now());
        family.setFamilyId(idGenerator.getNumberId());
        family.setFamilyGrade(1);
        family.setFamilyJoin(0);
        family.setFamilyUserNumber(1);
        family.setFamilyQrcode("/family/joinFamily?familyId="+family.getFamilyId());
        FamilyDetailEntity familyDetailEntity = new FamilyDetailEntity();
        familyDetailEntity.setIsAdmin(2);
        familyDetailEntity.setUserId(userId);
        familyDetailEntity.setFamilyDetailsId(idGenerator.getNumberId());
        familyDetailEntity.setFamilyId(family.getFamilyId());
        int insertFamily = familyDao.insert(family);
        int insertFamilyDetailEntity = familyDetailDao.insert(familyDetailEntity);
        if(insertFamily<1||insertFamilyDetailEntity<1){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"创建家族失败");
        }
    }

    @Override
    public Map<String,Object> getFamilyInfo(Long userId) {
        FamilyDetailEntity familyDetailEntity = familyDetailDao.selectOne(new QueryWrapper<FamilyDetailEntity>().lambda().eq(FamilyDetailEntity::getUserId, userId));
        if(familyDetailEntity==null){
            throw  new ApplicationException(CodeType.RESOURCES_NOT_FIND,"您当前还未加入任何家族");
        }
        Map<String,Object> map = new HashMap<>(2);
        //获取用户信息
        List<FamilyUsersVo> familyUsersVos = familyDetailDao.selectFamilyUser(familyDetailEntity.getFamilyId());
        FamilyEntity familyEntity = familyDao.selectOne(new QueryWrapper<FamilyEntity>().lambda().eq(FamilyEntity::getFamilyId, familyDetailEntity.getFamilyId()));
        map.put("family",familyEntity);
        map.put("isAdmin",familyDetailEntity.getIsAdmin());
        map.put("user",familyUsersVos);
        return map;
    }

    @Override
    public Map<String,Object> getMyFamily(Long userId) {
        FamilyDetailEntity familyDetailEntity = familyDetailDao.selectOne(new QueryWrapper<FamilyDetailEntity>().lambda().eq(FamilyDetailEntity::getUserId, userId));
        if(familyDetailEntity==null){
            throw  new ApplicationException(CodeType.RESOURCES_NOT_FIND,"还未加入家族");
        }
        Map<String,Object> map = new HashMap<>();
        //家族基本信息
        FamilyEntity familyEntity = familyDao.selectOne(new QueryWrapper<FamilyEntity>().lambda().eq(FamilyEntity::getFamilyId, familyDetailEntity.getFamilyId()));
        map.put("family",familyEntity);
        //头像 9张
        map.put("headImg",familyDetailDao.getFamilyHeadImg(familyEntity.getFamilyId()));
        //家族族长信息
        map.put("admin",familyDetailDao.selectPatriarch(familyEntity.getFamilyId()));
        return map;
    }

    @Override
    public void exitFamily(Long userId) {
        FamilyDetailEntity familyDetailEntity = familyDetailDao.selectOne(new QueryWrapper<FamilyDetailEntity>().lambda().eq(FamilyDetailEntity::getUserId, userId));
        if(familyDetailEntity==null){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"你不是该家族成员");
        }
        FamilyEntity familyEntity = familyDao.selectOne(new QueryWrapper<FamilyEntity>().lambda().eq(FamilyEntity::getFamilyId, familyDetailEntity.getFamilyId()));
        if(familyDetailDao.delete(new QueryWrapper<FamilyDetailEntity>().lambda().eq(FamilyDetailEntity::getUserId, userId))<1){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"退出家族失败");
        }
        familyEntity.setFamilyUserNumber(familyEntity.getFamilyUserNumber()-1);
        familyDao.updateById(familyEntity);
    }

    @Override
    public List<HotFamilyVo> getHotFamily() {
        return familyDao.getHotFamily();
    }

    @Override
    public void joinFamily(Long userId, Long familyId) {
        //家族是否存在
        FamilyEntity familyEntity = familyDao.selectOne(new QueryWrapper<FamilyEntity>().lambda().eq(FamilyEntity::getFamilyId, familyId));
        if(familyEntity==null){
            throw  new ApplicationException(CodeType.RESOURCES_NOT_FIND,"未找到当前家族");
        }
        //判断是否有家族 只能有一个家族
        FamilyDetailEntity familyDetailEntity = familyDetailDao.selectOne(new QueryWrapper<FamilyDetailEntity>().lambda().eq(FamilyDetailEntity::getUserId,userId));
        if(familyDetailEntity!=null){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"请退出当前家族后再试");
        }
        //家族人数加1
        familyEntity.setFamilyUserNumber(familyEntity.getFamilyUserNumber()+1);
        //插入记录
        FamilyDetailEntity familyDetail = new FamilyDetailEntity();
        familyDetail.setFamilyId(familyId);
        familyDetail.setUserId(userId);
        familyDetail.setIsAdmin(0);
        familyDetail.setFamilyDetailsId(idGenerator.getNumberId());
        int insert = familyDetailDao.insert(familyDetail);
        if(insert<1||familyDao.updateById(familyEntity)<1){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"加入家族失败");
        }
    }

    @Override
    public void setAdmin(Long userId,Long setUserId) {
        //查询是否族长
        FamilyDetailEntity user = familyDetailDao.selectOne(new QueryWrapper<FamilyDetailEntity>().lambda().eq(FamilyDetailEntity::getUserId, userId));
        if(user==null||user.getIsAdmin()!=2){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"您不是族长，没有权限");
        }
        //是否同一个家族
        FamilyDetailEntity setUser = familyDetailDao.selectOne(new QueryWrapper<FamilyDetailEntity>().lambda().eq(FamilyDetailEntity::getUserId, setUserId));
        if(setUser==null||!setUser.getFamilyId().equals(user.getFamilyId())){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"用户不属于您的家族");
        }
        //是管理员则弄为普通成员 则相反
        setUser.setIsAdmin(setUser.getIsAdmin()==0?1:0);
        int i = familyDetailDao.updateById(setUser);
        if(i<1){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"设置权限失败");
        }
    }

    @Override
    public void kickOutUser(Long userId, Long outUserId) {
        //查询是否族长
        FamilyDetailEntity user = familyDetailDao.selectOne(new QueryWrapper<FamilyDetailEntity>().lambda().eq(FamilyDetailEntity::getUserId, userId));
        if(user==null||user.getIsAdmin()!=2){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"您不是族长，没有权限");
        }
        //是否同一个家族
        FamilyDetailEntity outUser = familyDetailDao.selectOne(new QueryWrapper<FamilyDetailEntity>().lambda().eq(FamilyDetailEntity::getUserId, outUserId));
        if(outUser==null||!outUser.getFamilyId().equals(user.getFamilyId())){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"用户不属于您的家族");
        }
        int i = familyDetailDao.deleteById(outUser.getFamilyDetailsId());
        FamilyEntity familyEntity = familyDao.selectById(user.getFamilyId());
        familyEntity.setFamilyUserNumber(familyEntity.getFamilyUserNumber()-1);
        if(i<1||familyDao.updateById(familyEntity)<1){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"踢出成员失败");
        }
    }
}
