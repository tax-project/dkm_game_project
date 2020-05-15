package com.dkm.backpack.service.impl;

import com.dkm.backpack.entity.vo.BackpackItemVO;
import com.dkm.backpack.service.IBackpackService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.pets.entity.dto.BackPackFood;
import com.dkm.pets.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HuangJie
 * @Date: 2020/5/14 15:12
 * @Version: 1.0V
 */
@Service
public class BackpackServiceImpl implements IBackpackService {

    private final static String FOOD_TABLE = "";

    @Autowired
    private LocalUser localUser;

    @Autowired
    private FoodService foodService;

    @Override
    public void allBackpackItem() {
        UserLoginQuery localUserUser = localUser.getUser();
        assert localUserUser!=null;
        Long userId = localUserUser.getId();
        ArrayList<BackpackItemVO> backpackItemVOS = new ArrayList<>();

        List<BackPackFood> packFood = foodService.getPackFood(userId);
        for (BackPackFood backPackFood : packFood){
            BackpackItemVO backpackItemVO = new BackpackItemVO();
            backpackItemVO.setItemId(backPackFood.getId());
            backpackItemVO.setItemImageUrl(backPackFood.getFoodUrl());
            backpackItemVO.setItemName(backPackFood.getFoodName());
            backpackItemVO.setItemNumber(backPackFood.getFoodNumber());
            backpackItemVO.setItemGold(backPackFood.getFoodGold() );
            backpackItemVO.setItemTableNumber(1);
            backpackItemVOS.add(backpackItemVO);
        }

    }
}