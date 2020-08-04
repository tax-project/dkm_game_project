package com.dkm.admin.services;


import com.dkm.admin.entities.vo.UserLoginResultVo;
import com.dkm.admin.entities.vo.UserLoginVo;

public interface IAdminUserLoginService {
    UserLoginResultVo login(UserLoginVo loginVo);
    boolean checkToken(String token);
}
