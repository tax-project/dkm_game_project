package com.dkm.backpack.service.impl;

import com.dkm.backpack.entity.bo.AddBackpackItemBO;
import com.dkm.backpack.service.IBackpackService;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public void addBackpackItem(AddBackpackItemBO backpackItemBO) {
        UserLoginQuery localUserUser = localUser.getUser();
        assert localUserUser!=null;

        switch (backpackItemBO.getItemNumber()){
            case 1:
                //食物
                break;
            case 2:
                //物品
                break;
            case 3:
                //衣服
                break;
            default:
                throw new ApplicationException(CodeType.SERVICE_ERROR,"itemNumber参数越界");
        }
    }
}