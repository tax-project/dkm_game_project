package com.dkm.wechat.util;

import com.dkm.config.RedisConfig;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.utils.JwtUtils;
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
   private JwtUtils jwtUtil;

   public String getToken (UserBO bo) {
      UserLoginQuery query = new UserLoginQuery();
      query.setId(bo.getUserId());
      query.setWxOpenId(bo.getWeChatOpenId());
      query.setWxNickName(bo.getWeChatNickName());
      return jwtUtil.createJwt(query);
   }



}
