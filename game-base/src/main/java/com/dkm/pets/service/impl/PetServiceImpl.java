package com.dkm.pets.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.ResourceFeignClient;
import com.dkm.pets.dao.FoodDetailMapper;
import com.dkm.pets.dao.PetsMapper;
import com.dkm.pets.entity.FoodDetailEntity;
import com.dkm.pets.entity.PetUserEntity;
import com.dkm.pets.entity.dto.EatFoodDto;
import com.dkm.pets.entity.dto.PetsDto;
import com.dkm.pets.entity.dto.UserInfo;
import com.dkm.pets.entity.vo.FeedPetInfoVo;
import com.dkm.pets.service.PetService;
import com.dkm.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhd
 * @date 2020/5/9 9:17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PetServiceImpl implements PetService {
    @Resource
    private PetsMapper petsMapper;

    @Resource
    private FoodDetailMapper foodDetailMapper;

    @Resource
    private IdGenerator idGenerator;

    @Autowired
    private ResourceFeignClient resourceFeignClient;

    /**
     * 获取宠物页面所需信息
     * @param userId
     * @return
     */
    @Override
    public Map<String,Object> getAllPets(Long userId) {
        Map<String,Object> map = new HashMap<>();
        //获取用户信息
        UserInfo userInfo = petsMapper.findUserInfo(userId);
        map.put("userInfo",userInfo);
        //计算等级宠物数
        Integer petCount =Math.min(userInfo.getUserInfoGrade()/5+1,3) ;
        //查询宠物数
        Integer count = petsMapper.selectCount(new QueryWrapper<PetUserEntity>().lambda().eq(PetUserEntity::getUserId, userId));
        if( count< petCount){
            //当前宠物小于应有宠物 >>还有宠物未解锁 >>添加应有宠物
            List<PetUserEntity> list = new ArrayList<>();
            for (int i = count; i < petCount; i++) {
                PetUserEntity pet = new PetUserEntity();
                pet.setPGrade(1);
                pet.setPNowFood(0);
                pet.setUserId(userId);
                pet.setPId(idGenerator.getNumberId());
                pet.setPetId(i+1L);
                list.add(pet);
            }
            petsMapper.insertList(list);
        }
        //查询食物信息
        Map<String, Object> returnData = null;
        try {
            returnData = resourceFeignClient.selectUserId();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(CodeType.DATABASE_ERROR);
        }
        if(returnData==null||returnData.get("fail")!=null){
            throw new ApplicationException(CodeType.SERVICE_ERROR);
        }
        map.put("foodInfo",returnData.get("dataOne"));
        //查询宠物信息
        List<PetsDto> petInfo = petsMapper.findById(userId);
        //食物信息
        List<FoodDetailEntity> foodDetailEntities = foodDetailMapper.selectList(null);
        for (PetsDto petsDto : petInfo) {
            //获取喂食进度  >>  每5级加一次喂食
            double roles = (double) petsDto.getPGrade()/5+2;
            petsDto.setSchedule( new BigDecimal(petsDto.getPNowFood()/roles).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
            //根据宠物等级获取喂食规则
            List<EatFoodDto> role = new ArrayList<>();
            //10级之后才喂食鱼干 >> 喂食种类
            int feed = petsDto.getPGrade()>=10?2:1;
            //食物数量
            int c = petsDto.getPGrade() / 10 + 1;
            for (int i = 0; i < feed; i++) {
                FoodDetailEntity foodDetailEntity = foodDetailEntities.get(i);
                EatFoodDto eatFoodDto = new EatFoodDto();
                eatFoodDto.setFoodUrl(foodDetailEntity.getFoodUrl());
                eatFoodDto.setFoodName(foodDetailEntity.getFoodName());
                //食物数量
                eatFoodDto.setENumber(i==0?c:c-1);
                eatFoodDto.setFoodId(foodDetailEntity.getFoodId());
                role.add(eatFoodDto);
            }
            petsDto.setEatFood(role);
        }
        map.put("petInfo",petInfo);
        return map;
    }

    /**
     * 喂食
     */
    @Override
    public void feedPet(FeedPetInfoVo petInfoVo) {
        try {
            //修改宠物喂食进度
            PetUserEntity petUserEntity = petsMapper.selectById(petInfoVo.getPId());
            petUserEntity.setPNowFood(petUserEntity.getPNowFood()+1);
            int feed = petsMapper.updateById(petUserEntity);
            //更新背包食物
            resourceFeignClient.updateIsva(petInfoVo.getPId(), -petUserEntity.getPGrade() / 5 + 1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(CodeType.SERVICE_ERROR,"更新信息失败");
        }
    }

    /**
     * 宠物升级
     * @param petInfoVo
     */
    @Override
    public void petLevelUp(FeedPetInfoVo petInfoVo) {
        try {
            //修改宠物喂食进度>> 提升等级
            PetUserEntity petUserEntity = petsMapper.selectById(petInfoVo.getPId());
            petUserEntity.setPNowFood(0);
            petUserEntity.setPGrade(petUserEntity.getPGrade()+1);
            int feed = petsMapper.updateById(petUserEntity);
            //更新背包食物
            resourceFeignClient.updateIsva(petInfoVo.getPId(), -petUserEntity.getPGrade() / 5 + 1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(CodeType.SERVICE_ERROR,"更新信息失败");
        }
    }
}
