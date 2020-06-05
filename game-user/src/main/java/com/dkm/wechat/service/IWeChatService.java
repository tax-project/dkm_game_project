package com.dkm.wechat.service;


import com.dkm.entity.bo.UserHeardUrlBo;
import com.dkm.entity.bo.UserInfoBo;
import com.dkm.entity.bo.UserInfoQueryBo;
import com.dkm.wechat.entity.vo.UserLoginVo;
import com.dkm.wechat.entity.vo.UserRegisterVo;

import java.util.List;
import java.util.Map;

/**
 * @Author: HuangJie
 * @Date: 2020/5/8 16:37
 * @Version: 1.0V
 */
public interface IWeChatService {
    /**
     * 微信登录
     * @param code 登录code
     * @return 用户信息和token
     */
    Map<String,Object> weChatLoginUserInfo(String code);

    /**
     * qf
     * 注册
     * @param vo 注册的参数
     */
    void userRegister (UserRegisterVo vo);

    /**
     *  qf
     *  登录
     * @param vo 登录的参数
     * @return token以及用户信息
     */
    Map<String,Object> userLogin (UserLoginVo vo);

    /**
     *  查询所有用户信息
     * @param id 用户id
     * @return 用户所有信息
     */
    UserInfoQueryBo queryUser (Long id);

    /**
     *  根据用户查询所有头像
     * @param list
     * @return
     */
    List<UserHeardUrlBo> queryAllHeardByUserId (List<Long> list);
}
