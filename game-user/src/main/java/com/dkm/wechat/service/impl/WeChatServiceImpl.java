package com.dkm.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.config.RedisConfig;
import com.dkm.constanct.CodeType;
import com.dkm.data.Result;
import com.dkm.entity.bo.UserInfoBo;
import com.dkm.entity.websocket.MsgInfo;
import com.dkm.exception.ApplicationException;
import com.dkm.feign.FriendFeignClient;
import com.dkm.feign.entity.FriendNotOnlineVo;
import com.dkm.userInfo.service.IUserInfoService;
import com.dkm.utils.IdGenerator;
import com.dkm.utils.ShaUtils;
import com.dkm.wechat.dao.UserMapper;
import com.dkm.wechat.entity.User;
import com.dkm.wechat.entity.bo.UserBO;
import com.dkm.wechat.entity.vo.UserLoginVo;
import com.dkm.wechat.entity.vo.UserRegisterVo;
import com.dkm.wechat.service.IWeChatService;
import com.dkm.wechat.util.CreateToken;
import com.dkm.wechat.util.WeChatUtil;
import com.dkm.wechat.util.bo.WeChatUtilBO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: HuangJie
 * @Date: 2020/5/8 16:37
 * @Version: 1.0V
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WeChatServiceImpl extends ServiceImpl<UserMapper,User> implements IWeChatService  {

    @Autowired
    private WeChatUtil weChatUtil;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private CreateToken createToken;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private FriendFeignClient friendFeignClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisConfig redisConfig;

    @Override
    public Map<String, Object> weChatLoginUserInfo(String code) {
        UserBO resultBO = new UserBO();
        try {
            WeChatUtilBO weChatUtilBO = weChatUtil.codeToUserInfo(code);
            User user = new User();
            BeanUtils.copyProperties(weChatUtilBO, user);
            //查询用户是否存在
            UserBO userBO = baseMapper.queryUserInfo(weChatUtilBO.getWeChatOpenId());
            if (userBO == null) {
                //用户不存在，创建
                long userId = idGenerator.getNumberId();
                user.setUserIsEffective(0);
                user.setUserId(userId);
                int insert = baseMapper.insert(user);
                if (insert == 0) {
                    throw new ApplicationException(CodeType.SERVICE_ERROR, "用户创建登录失败，请重新登录");
                }

                //同时初始化用户详情信息
                userInfoService.insertUserInfo(userId);
            } else {
                //用户存在更新
                LambdaQueryWrapper<User> updateWrapper = new LambdaQueryWrapper<>();
                updateWrapper.eq(User::getUserId, userBO.getUserId());
                int update = baseMapper.update(user, updateWrapper);
                if (update == 0) {
                    throw new ApplicationException(CodeType.SERVICE_ERROR, "用户更新登录失败，请重新登录");
                }
                BeanUtils.copyProperties(userBO, resultBO);

                //查询用户是否有未在线消息
                Result<List<FriendNotOnlineVo>> result = friendFeignClient.queryNotOnline(userBO.getUserId());

                List<FriendNotOnlineVo> list = result.getData();

                if (null != list && list.size() != 0) {
                    //有离线消息,当前该账号未在线，将未在线消息发送给客户端
                    List<Long> longList = new ArrayList<>();
                    for (FriendNotOnlineVo onlineVo : list) {
                        MsgInfo msgInfo = new MsgInfo();
                        msgInfo.setFromId(onlineVo.getFromId());
                        msgInfo.setToId(onlineVo.getToId());
                        msgInfo.setMsg(onlineVo.getContent());
                        msgInfo.setSendDate(onlineVo.getCreateDate());
                        //离线信息
                        msgInfo.setType(onlineVo.getType());
                        //将消息更改成已读
                        longList.add(onlineVo.getToId());
                        rabbitTemplate.convertAndSend("msg_fanoutExchange", "", JSON.toJSONString(msgInfo));
                    }

                    //更改数据库的未读状态
                    friendFeignClient.updateLookStatus(longList);
                }
            }
                //拷贝新的微信信息，如果是创建用户的话还有新的ID
                BeanUtils.copyProperties(user, resultBO);

                //将设备Id和用户Id存入redis中
                String cid = idGenerator.getUuid();
                redisConfig.setString(resultBO.getUserId(),cid);
                //生成Token
                String token = createToken.getToken(resultBO);
                HashMap<String, Object> resultMap = new HashMap<>(3);
                resultMap.put("token", token);
                resultMap.put("resultUser", resultBO);
                resultMap.put("cid",cid);
                return resultMap;
            } catch(IOException e){
                throw new ApplicationException(CodeType.SERVICE_ERROR, "网络请求错误");
            }
        }


    /**
     *  注册 qf
     * @param vo 注册的参数
     */
    @Override
    public synchronized void userRegister(UserRegisterVo vo) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
              .eq(User::getWeChatOpenId,vo.getUserName());

        User user = baseMapper.selectOne(wrapper);

        if (user != null) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "该账号已被注册");
        }

        //注册
        User user1 = new User();
        long id = idGenerator.getNumberId();
        user1.setUserId(id);
        user1.setUserIsEffective(0);
        user1.setWeChatOpenId(vo.getUserName());
        user1.setWeChatNickName(vo.getNickName());
        user1.setUserRemark(ShaUtils.getSha1(vo.getPassword()));

        int insert = baseMapper.insert(user1);

        if (insert <= 0) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "注册失败");
        }

        //同时初始化用户详情信息
        userInfoService.insertUserInfo(id);
    }

    /**
     * 登录
     * @param vo 登录的参数
     * @return qf
     */
    @Override
    public Map<String, Object> userLogin(UserLoginVo vo) {

        UserBO userBO = baseMapper.queryUserInfo(vo.getUserName());

        if (userBO == null) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "该账号不存在");
        }

        if (!ShaUtils.getSha1(vo.getPassword()).equals(userBO.getUserRemark())) {
            throw new ApplicationException(CodeType.SERVICE_ERROR, "密码错误");
        }

        //查询用户是否有未在线消息
        Result<List<FriendNotOnlineVo>> result = friendFeignClient.queryNotOnline(userBO.getUserId());

        List<FriendNotOnlineVo> list = result.getData();

        if (null != list && list.size() != 0) {
            //有离线消息,当前该账号未在线，将未在线消息发送给客户端
            List<Long> longList = new ArrayList<>();
            for (FriendNotOnlineVo onlineVo : list) {
                MsgInfo msgInfo = new MsgInfo();
                msgInfo.setFromId(onlineVo.getFromId());
                msgInfo.setToId(onlineVo.getToId());
                msgInfo.setMsg(onlineVo.getContent());
                msgInfo.setSendDate(onlineVo.getCreateDate());
                //离线信息
                msgInfo.setType(onlineVo.getType());
                //将消息更改成已读
                longList.add(onlineVo.getToId());
                rabbitTemplate.convertAndSend("msg_fanoutExchange", "", JSON.toJSONString(msgInfo));
            }

            //更改数据库的未读状态
            friendFeignClient.updateLookStatus(longList);
        }

        //将设备Id和用户Id存入redis中
        String cid = idGenerator.getUuid();
        redisConfig.setString(userBO.getUserId(),cid);

        //生成Token
        String token = createToken.getToken(userBO);
        HashMap<String, Object> resultMap = new HashMap<>(3);
        resultMap.put("token",token);
        resultMap.put("resultUser",userBO);
        resultMap.put("cid",cid);
        return resultMap;
    }

    /**
     * qf
     *  查询所有用户信息
     * @param id 用户id
     * @return 用户所有信息
     */
    @Override
    public UserInfoBo queryUser(Long id) {
        return baseMapper.queryUser(id);
    }
}
