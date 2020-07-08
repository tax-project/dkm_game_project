package com.dkm.feign.fallback;

import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.feign.FamilyFeiginClient;
import com.dkm.feign.entity.UserCenterFamilyVo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-06-22 16:27
 **/
@Component
public class FamilyFeiginClientFallback implements FamilyFeiginClient {
   /* @GetMapping("/family/getUserCenterFamily")
    @Override
    public Result<UserCenterFamilyVo> getUserCenterFamily(Long userId) {
        return Result.fail(CodeType.DATABASE_ERROR);
    }*/
}
