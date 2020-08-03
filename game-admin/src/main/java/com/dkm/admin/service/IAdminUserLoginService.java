package com.dkm.admin.service;


import com.dkm.admin.mapper.vo.UserLoginResultVo;
import com.dkm.admin.mapper.vo.UserLoginVo;

public interface IAdminUserLoginService {
    UserLoginResultVo login(UserLoginVo loginVo);

    boolean checkToken(String token);
}
