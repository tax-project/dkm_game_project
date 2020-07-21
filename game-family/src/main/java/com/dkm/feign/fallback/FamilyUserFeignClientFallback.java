package com.dkm.feign.fallback;

import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.diggings.bean.vo.RenownVo;
import com.dkm.diggings.bean.vo.UserInfoBO;
import com.dkm.diggings.bean.vo.UserInfosVo;
import com.dkm.feign.FamilyUserFeignClient;
import org.springframework.stereotype.Component;

/**
 * @author qf
 * @date 2020/6/11
 * @vesion 1.0
 **/
@Component
public class FamilyUserFeignClientFallback implements FamilyUserFeignClient {
    @Override
    public Result<RenownVo> queryUserSection(long userId) {
        return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
    }


    @Override
    public Result<String> update(UserInfoBO userInfoBO) {
        return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
    }

    @Override
    public Result<UserInfosVo> queryUser(long id) {
        return Result.fail(CodeType.FEIGN_CONNECT_ERROR);
    }
}