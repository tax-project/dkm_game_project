package com.dkm.wechat.util;

import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.utils.JwtUtil;
import com.dkm.wechat.entity.bo.UserBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author qf
 * @date 2020/4/13
 * @vesion 1.0
 **/
@Component
public class CreateToken {

   @Autowired
   private JwtUtil jwtUtil;

   public String getToken (UserBO bo) {
      UserLoginQuery query = new UserLoginQuery();
      query.setId(bo.getUserId());
      query.setWxOpenId(bo.getWeChatOpenId());
      query.setUserLevel(bo.getUserGrade());
      query.setWxNickName(bo.getWeChatNickName());
      query.setUserInfoIsVip(bo.getUserIsVip());
      //24小时过期时间
      return jwtUtil.createJWT(1000 * 60 * 60 * 24L, query);
   }



}
