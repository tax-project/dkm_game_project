package com.dkm.pets.service.impl;

import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.pets.dao.PetsMapper;
import com.dkm.pets.entity.dto.EatFoodDto;
import com.dkm.pets.entity.dto.PetsDto;
import com.dkm.pets.service.PetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
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

    /**
     * 获取宠物页面所需信息
     * @param userId
     * @return
     */
    @Override
    public Map<String,Object> getAllPets(Long userId) {
        List<PetsDto> petInfo = petsMapper.findById(userId,null);
        for (PetsDto petsDto : petInfo) {
            //根据宠物等级获取喂食规则
            petsDto.setEatFood(petsMapper.findEatFood(petsDto.getPGrade()));
        }
        Map<String,Object> map = new HashMap<>();
        map.put("petInfo",petInfo);
        map.put("userInfo",petsMapper.findUserInfo(userId));
        map.put("foodInfo",petsMapper.findFoodInfo(userId));
        return map;
    }

    /**
     * 喂食宠物操作
     * @return
     */
    @Override
    public Map<String,Object> feedPet(PetsDto petInfo,Long userId) {
        try {
            //更新食物信息
            int updateFood = 0;
            for (EatFoodDto f : petInfo.getEatFood()) {
                updateFood +=  petsMapper.updateFoodInfo(f.getENumber(), f.getFoodId(), userId);
            }
            //更新用户信息
            //声望 50+等级/10
            int renown = 50 + petInfo.getPGrade() / 10;
            if(updateFood<petInfo.getEatFood().size()||petsMapper.updateUserInfo(renown,userId)<1
                ||petsMapper.updatePetInfo(petInfo.getPNowFood()+1,null,petInfo.getPId())<1){
                throw new ApplicationException(CodeType.SERVICE_ERROR,"更新信息失败");
            }
            //更新后返回信息
            Map<String,Object> map = new HashMap<>();
            List<PetsDto> selectOne = petsMapper.findById(userId, petInfo.getPetId());
            selectOne.get(0).setEatFood(petsMapper.findEatFood(selectOne.get(0).getPGrade()));
            map.put("onePetInfo",selectOne);
            map.put("userInfo",petsMapper.findUserInfo(userId));
            map.put("foodInfo",petsMapper.findFoodInfo(userId));
            return map;
        } catch (ApplicationException e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new ApplicationException(CodeType.SERVICE_ERROR,"喂食失败");
        }
    }

    /**
     * 升级宠物
     * @param foodId
     * @param pId
     * @param userId
     * @return
     */
    @Override
    public Map<String,Object> getNeedInfo(Long foodId,Long pId, Long userId){
        try {
            //升级并清空进度
            Integer integer = petsMapper.updatePetInfo(0, 1, pId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
