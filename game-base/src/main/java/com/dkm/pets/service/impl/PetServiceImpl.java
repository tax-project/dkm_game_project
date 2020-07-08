package com.dkm.pets.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.config.RedisConfig;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.ResourceFeignClient;
import com.dkm.pets.dao.PetsMapper;
import com.dkm.pets.entity.PetUserEntity;
import com.dkm.pets.entity.dto.EatFoodDto;
import com.dkm.pets.entity.dto.PetsDto;
import com.dkm.pets.entity.dto.UserInfo;
import com.dkm.pets.entity.vo.FeedPetInfoVo;
import com.dkm.pets.entity.vo.TbEquipmentKnapsackVo;
import com.dkm.pets.service.PetService;
import com.dkm.turntable.dao.GoodsMapper;
import com.dkm.turntable.entity.GoodsEntity;
import com.dkm.utils.DateUtils;
import com.dkm.utils.IdGenerator;
import com.dkm.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    private RedisConfig redisConfig;

    @Resource
    private IdGenerator idGenerator;

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private ResourceFeignClient resourceFeignClient;

    /**
     * 获取宠物页面所需信息
     *
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> getAllPets(Long userId) {
        Map<String, Object> map = new HashMap<>();
        //查询食物信息
        Result<List<TbEquipmentKnapsackVo>> listResult = resourceFeignClient.selectUserIdAndFoodId(userId);
        if (listResult.getCode() != 0) {
            throw new ApplicationException(CodeType.SERVICE_ERROR,"获取不到食物信息");
        }
        //食物信息
        List<GoodsEntity> goodsEntities = goodsMapper.selectList(new LambdaQueryWrapper<GoodsEntity>().eq(GoodsEntity::getGoodType, 3));
        if(listResult.getData()==null||listResult.getData().size()<3){
            List<GoodsEntity> a = goodsEntities;
            //有部分食物>>添加完整
            if(listResult.getData()!=null){
                for (TbEquipmentKnapsackVo foodInfo : listResult.getData()) {
                    a = a.stream().filter(food -> !food.getId().equals(foodInfo.getFoodId())).collect(Collectors.toList());
                }
            }
            //没有食物
            a.forEach(foodDetailEntity -> {
                TbEquipmentKnapsackVo foodInfo = new TbEquipmentKnapsackVo();
                foodInfo.setFoodId(foodDetailEntity.getId());
                foodInfo.setName(foodDetailEntity.getName());
                foodInfo.setFoodNumber(0);
                foodInfo.setUrl(foodDetailEntity.getUrl());
                listResult.getData().add(foodInfo);
            });
        }
        map.put("foodInfo", listResult.getData());
        //获取用户信息
        UserInfo userInfo = petsMapper.findUserInfo(userId);
        map.put("userInfo", userInfo);
        //计算等级应有宠物数
        Integer petCount = Math.min(userInfo.getUserInfoGrade() / 5 + 1, 3);
        //获得查询宠物数
        Integer count = petsMapper.selectCount(new QueryWrapper<PetUserEntity>().lambda().eq(PetUserEntity::getUserId, userId));
        if (count < petCount) {
            //当前宠物小于应有宠物 >>还有宠物未解锁 >>添加应有宠物
            List<PetUserEntity> list = new ArrayList<>();
            for (int i = count; i < petCount; i++) {
                PetUserEntity pet = new PetUserEntity();
                pet.setPGrade(1);
                pet.setPNowFood(0);
                pet.setUserId(userId);
                pet.setPId(idGenerator.getNumberId());
                pet.setPetId(i + 1L);
                list.add(pet);
            }
            petsMapper.insertList(list);
        }
        //查询宠物信息
        List<PetsDto> petInfo = petsMapper.findById(userId);
        for (PetsDto petsDto : petInfo) {
            //获取喂食进度  >>  每5级加一次喂食
            double roles = (double) petsDto.getPGrade() / 5 + 2;
            petsDto.setSchedule(new BigDecimal(petsDto.getPNowFood() * 100 / roles).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            //根据宠物等级获取喂食规则
            List<EatFoodDto> role = new ArrayList<>();
            if (petsDto.getSchedule() >= 100) {
                //升级
                GoodsEntity foodDetailEntity = goodsEntities.get(goodsEntities.size()-1);
                EatFoodDto eatFoodDto = new EatFoodDto();
                eatFoodDto.setFoodUrl(foodDetailEntity.getUrl());
                eatFoodDto.setFoodName(foodDetailEntity.getName());
                eatFoodDto.setFoodId(foodDetailEntity.getId());
                //奶瓶数量
                eatFoodDto.setENumber(1);
                eatFoodDto.setFoodNumber(listResult.getData().stream().filter(item -> item.getFoodId()==3).collect(Collectors.toList()).get(0).getFoodNumber());
                role.add(eatFoodDto);
            } else {
                //10级之后才喂食鱼干 >> 喂食种类
                int feed = petsDto.getPGrade() >= 10 ? 2 : 1;
                //食物数量
                int c = petsDto.getPGrade() / 10 + 1;
                //喂食
                listResult.getData().forEach(food->{
                    for (int i = 0; i < feed; i++) {
                        GoodsEntity foodDetailEntity = goodsEntities.get(i);
                        EatFoodDto eatFoodDto = new EatFoodDto();
                        eatFoodDto.setFoodUrl(foodDetailEntity.getUrl());
                        eatFoodDto.setFoodName(foodDetailEntity.getName());
                        eatFoodDto.setFoodId(foodDetailEntity.getId());
                        //食物数量
                        eatFoodDto.setENumber(i == 0 ? c : c - 1);
                        if(foodDetailEntity.getId().equals(food.getFoodId())){
                            eatFoodDto.setFoodNumber(food.getFoodNumber());
                            role.add(eatFoodDto);
                        }
                    }
                });
            }
            petsDto.setEatFood(role);
        }
        map.put("petInfo", petInfo);
        return map;
    }

    /**
     * 喂食
     */
    @Override
    public void feedPet(FeedPetInfoVo petInfoVo) {
        //修改宠物喂食进度
        PetUserEntity petUserEntity = petsMapper.selectById(petInfoVo.getPId());
        petUserEntity.setPNowFood(petUserEntity.getPNowFood() + 1);
        Result result = resourceFeignClient.updateIsva(petInfoVo.getBeeTekId(), petInfoVo.getPGrade() / 10 + 1);
        //大于十级喂食鱼干
        Result result1 = null;
        if(petInfoVo.getPGrade() >= 10 && result.getCode() == 0){
            result1 = resourceFeignClient.updateIsva(petInfoVo.getFishTekId(), petInfoVo.getPGrade() / 10);
        }
        //更新背包食物
        if (petsMapper.updateUserRenown(petInfoVo.getUserId(), petInfoVo.getPGrade() / 5 + 50) < 1
                || petsMapper.updateById(petUserEntity) < 1 || result.getCode() != 0
                || (petInfoVo.getPGrade() >= 10 && result1.getCode() != 0)) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "喂食失败");
        }
        redisConfig.setString("pet"+petInfoVo.getUserId(),DateUtils.formatDateTime(LocalDateTime.now()));
    }

    /**
     * 宠物升级
     * @param petInfoVo
     */
    @Override
    public void petLevelUp(FeedPetInfoVo petInfoVo) {
        //修改宠物喂食进度>> 提升等级
        PetUserEntity petUserEntity = petsMapper.selectById(petInfoVo.getPId());
        petUserEntity.setPNowFood(0);
        petUserEntity.setPGrade(petUserEntity.getPGrade() + 1);
        //更新背包食物 >> 升级需要一个奶瓶
        if (petsMapper.updateUserRenown(petInfoVo.getUserId(), petInfoVo.getPGrade() / 5 + 70) < 1
                || petsMapper.updateById(petUserEntity) < 1
                || resourceFeignClient.updateIsva(petInfoVo.getMilkTekId(), 1).getCode() != 0) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "升级失败");
        }
    }

    @Override
    public List<PetsDto> getPetInfo(Long userId) {
        //获取用户信息
        UserInfo userInfo = petsMapper.findUserInfo(userId);
        //计算等级应有宠物数
        Integer petCount = Math.min(userInfo.getUserInfoGrade() / 5 + 1, 3);
        //获得查询宠物数
        Integer count = petsMapper.selectCount(new QueryWrapper<PetUserEntity>().lambda().eq(PetUserEntity::getUserId, userId));
        if (count < petCount) {
            //当前宠物小于应有宠物 >>还有宠物未解锁 >>添加应有宠物
            List<PetUserEntity> list = new ArrayList<>();
            for (int i = count; i < petCount; i++) {
                PetUserEntity pet = new PetUserEntity();
                pet.setPGrade(1);
                pet.setPNowFood(0);
                pet.setUserId(userId);
                pet.setPId(idGenerator.getNumberId());
                pet.setPetId(i + 1L);
                list.add(pet);
            }
            petsMapper.insertList(list);
        }
        return petsMapper.findById(userId);
    }

    @Override
    public String isHunger(Long userId) {
        String string = (String)redisConfig.getString("pet"+userId);
        if (StringUtils.isNotEmpty(string)&&DateUtils.parseDateTime(string).minusSeconds(-10).isBefore(LocalDateTime.now())){
            return "宠物已经饿的不行了！";
        }
        else throw new ApplicationException(CodeType.SERVICE_ERROR,"不需要喂食！");
    }

}
