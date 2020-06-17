package com.dkm.family.service.impl;

import com.alibaba.druid.util.Base64;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.family.dao.FamilyDao;
import com.dkm.family.dao.FamilyDetailDao;
import com.dkm.family.entity.FamilyDetailEntity;
import com.dkm.family.entity.FamilyEntity;
import com.dkm.family.entity.vo.FamilyGoldInfoVo;
import com.dkm.family.entity.vo.FamilyImgsVo;
import com.dkm.family.entity.vo.FamilyUsersVo;
import com.dkm.family.entity.vo.HotFamilyVo;
import com.dkm.family.service.FamilyService;
import com.dkm.utils.IdGenerator;
import com.dkm.utils.QrCodeUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: game_project
 * @description: fimaly
 * @author: zhd
 * @create: 2020-05-20 10:59
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class FamilyServiceImpl implements FamilyService {
    @Value("${urls.family}")
    private String familyUrl;
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
        family.setFamilyQrcode(familyUrl+"/family/joinFamily?familyId="+family.getFamilyId());
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
    public Map<String,Object> otherFamilyInfo(Long familyId) {
        FamilyEntity familyEntity1 = familyDao.selectById(familyId);
        if(familyEntity1==null){
            throw  new ApplicationException(CodeType.RESOURCES_NOT_FIND,"该家族不存在");
        }
        Map<String,Object> map = new HashMap<>(2);
        //获取用户信息
        List<FamilyUsersVo> familyUsersVos = familyDetailDao.selectFamilyUser(familyEntity1.getFamilyId());
        map.put("family",familyEntity1);
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
        if(familyDetailDao.delete(new QueryWrapper<FamilyDetailEntity>().lambda().eq(FamilyDetailEntity::getUserId, userId))<1){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"退出家族失败");
        }
        if(familyDetailEntity.getIsAdmin()==2){
            int i = familyDao.deleteById(familyDetailEntity.getFamilyId());
            int delete = familyDetailDao.delete(new QueryWrapper<FamilyDetailEntity>().lambda().eq(FamilyDetailEntity::getFamilyId, familyDetailEntity.getFamilyId()));
            if(i<1||delete<1){
                throw new ApplicationException(CodeType.SERVICE_ERROR,"解散家族失败");
            }
            return;
        }
        FamilyEntity familyEntity = familyDao.selectOne(new QueryWrapper<FamilyEntity>().lambda().eq(FamilyEntity::getFamilyId, familyDetailEntity.getFamilyId()));
        familyEntity.setFamilyUserNumber(familyEntity.getFamilyUserNumber()-1);
        familyDao.updateById(familyEntity);
    }

    @Override
    public List<HotFamilyVo> getHotFamily() {
        //获取热门家族
        List<HotFamilyVo> hotFamily = familyDao.getHotFamily();
        List<Long> collect = hotFamily.stream().mapToLong(HotFamilyVo::getFamilyId).boxed().collect(Collectors.toList());
        //根据家族id查询人员头像
        Map<Long, List<String>> collect1 = familyDao.getImgs(collect).stream().collect(Collectors.groupingBy(FamilyImgsVo::getFamilyId,Collectors.mapping(FamilyImgsVo::getWeChatHeadImgUrl,Collectors.toList())));
        //整合头像并返回结果
        hotFamily.forEach(hotFamilyVo->{
            List<String> strings = collect1.get(hotFamilyVo.getFamilyId());
            hotFamilyVo.setImgs(strings.subList(0, Math.min(strings.size(), 3)));
        });
        return hotFamily;
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
        if(userId.equals(outUserId)){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"不能踢出自己");
        }
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

    @Override
    public List<Long> getFamilyUserIds(Long familyId) {
        List<FamilyDetailEntity> familyDetailEntities = familyDetailDao.selectList(new QueryWrapper<FamilyDetailEntity>().lambda().eq(FamilyDetailEntity::getFamilyId, familyId));
        return familyDetailEntities.stream().mapToLong(FamilyDetailEntity::getUserId).boxed().collect(Collectors.toList());
    }

    @Override
    public List<FamilyGoldInfoVo> selectFamilyGoldInfo() {
        return familyDao.selectFamilyGoldInfo();
    }

    @Override
    public String getQrcode(Long familyId) {
        String content = familyUrl + "/family/joinFamily?familyId=" + familyId;
        //图片的宽度
        int width=300;
        //图片的高度
        int height=300;
        //图片的格式
        String format="png";

        ByteArrayOutputStream os =new ByteArrayOutputStream();

        /**
         * 定义二维码的参数
         */
        HashMap hints=new HashMap();
        //指定字符编码为“utf-8”
        hints.put(EncodeHintType.CHARACTER_SET,"utf-8");
        //指定二维码的纠错等级为中级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        //设置图片的边距
        hints.put(EncodeHintType.MARGIN, 2);

        /**
         * 生成二维码
         */
        try {
            BitMatrix bitMatrix=new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height,hints);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ImageIO.write(bufferedImage,format,os);
            return "data:image/png;base64," + Base64.byteArrayToBase64(os.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void transfer(Long userId, Long setUserId) {
        List<FamilyDetailEntity> familyDetailEntities = familyDetailDao.selectList(new LambdaQueryWrapper<FamilyDetailEntity>().in(FamilyDetailEntity::getUserId, Stream.of(userId, setUserId).collect(Collectors.toList())));
        if(familyDetailEntities==null||familyDetailEntities.size()!=2){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"转让失败");
        }
        if(familyDetailEntities.stream().noneMatch(a->a.getIsAdmin()==2)){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"您不是族长");
        }
        familyDetailEntities.forEach(a-> a.setIsAdmin(a.getIsAdmin()==2?1:2));
        if(familyDetailDao.updateById(familyDetailEntities.get(0))<1||familyDetailDao.updateById(familyDetailEntities.get(1))<1){
            throw new ApplicationException(CodeType.SERVICE_ERROR,"转让失败");
        }
    }
}
