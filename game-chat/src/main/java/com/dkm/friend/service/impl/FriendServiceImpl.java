package com.dkm.friend.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.constanct.CodeType;
import com.dkm.entity.websocket.MsgInfo;
import com.dkm.exception.ApplicationException;
import com.dkm.friend.dao.FriendMapper;
import com.dkm.friend.entity.Friend;
import com.dkm.friend.entity.bo.FriendAllQueryBo;
import com.dkm.friend.entity.bo.FriendBo;
import com.dkm.friend.entity.bo.FriendToWithBo;
import com.dkm.friend.entity.vo.FriendAllListVo;
import com.dkm.friend.entity.vo.FriendNotOnlineVo;
import com.dkm.friend.entity.vo.FriendVo;
import com.dkm.friend.entity.vo.IdVo;
import com.dkm.friend.service.IFriendNotOnlineService;
import com.dkm.friend.service.IFriendRequestService;
import com.dkm.friend.service.IFriendService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author qf
 * @date 2020/5/11
 * @vesion 1.0
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements IFriendService {

   @Autowired
   private IdGenerator idGenerator;

   @Autowired
   private LocalUser localUser;

   @Autowired
   private IFriendRequestService friendRequestService;

   @Autowired
   private IFriendNotOnlineService friendNotOnlineService;

   @Autowired
   private RabbitTemplate rabbitTemplate;

   /**
    * 成为好友
    * @param vo
    */
   @Override
   public void insertFriend(FriendVo vo) {

      Friend friend = new Friend();

      friend.setId(idGenerator.getNumberId());
      friend.setStatus(0);
      friend.setCreateDate(LocalDateTime.now());
      friend.setFromId(vo.getFromId());
      friend.setToId(vo.getToId());

      int insert = baseMapper.insert(friend);

      if (insert <= 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "成为好友失败");
      }

      //两个人同时成为好友
      friend.setId(idGenerator.getNumberId());
      friend.setStatus(0);
      friend.setFromId(vo.getToId());
      friend.setToId(vo.getFromId());

      int i = baseMapper.insert(friend);

      if (i <= 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "成为好友失败");
      }

   }

   /**
    *  删除好友
    * @param toId 要删除的人的id
    */
   @Override
   public void deleteFriend(Long toId) {

      UserLoginQuery user = localUser.getUser();

      //先删除好友表里面的好友
      LambdaQueryWrapper<Friend> wrapper = new LambdaQueryWrapper<Friend>()
            .eq(Friend::getFromId,user.getId())
            .eq(Friend::getToId,toId);

      int delete = baseMapper.delete(wrapper);

      if (delete <= 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "删除失败");
      }

      LambdaQueryWrapper<Friend> lambdaQueryWrapper = new LambdaQueryWrapper<Friend>()
            .eq(Friend::getFromId,toId)
            .eq(Friend::getToId,user.getId());

      int delete1 = baseMapper.delete(lambdaQueryWrapper);

      if (delete1 <= 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "删除失败");
      }

      //删除申请表中的信息
      friendRequestService.deleteRequestInfo(user.getId(),toId);

   }


   /**
    * 展示全部好友
    * @return 所有好友信息
    */
   @Override
   public List<FriendAllListVo> listAllFriend() {
      UserLoginQuery user = localUser.getUser();

      LambdaQueryWrapper<Friend> wrapper = new LambdaQueryWrapper<Friend>()
            .eq(Friend::getFromId,user.getId());

      List<Friend> friendList = baseMapper.selectList(wrapper);

      if (null == friendList || friendList.size() == 0) {
         return null;
      }

      List<IdVo> list = new ArrayList<>();
      for (Friend friend : friendList) {
         IdVo vo = new IdVo();
         vo.setFromId(friend.getToId());
         list.add(vo);
      }

      //查询所有好友的信息
      return baseMapper.queryAllFriend(list);
   }


   @Override
   public List<FriendBo> listAllProblemFriend() {
      //得到用户信息
      UserLoginQuery user = localUser.getUser();

      LambdaQueryWrapper<Friend> wrapper = new LambdaQueryWrapper<Friend>()
            .eq(Friend::getFromId,user.getId());

      List<Friend> friendList = baseMapper.selectList(wrapper);

      if (null == friendList || friendList.size() == 0) {
         return null;
      }

      List<IdVo> list = new ArrayList<>();
      for (Friend friend : friendList) {
         IdVo vo = new IdVo();
         vo.setFromId(friend.getToId());
         list.add(vo);
      }

      //查询所有的好友列表
      //随机取10条数据
      List<FriendAllListVo> voList = baseMapper.queryRandomFriend(list);

      if (null == voList || voList.size() == 0) {
         return null;
      }

      //得到登陆人所有的好友列表
      List<IdVo> myFriendList = new ArrayList<>();
      for (FriendAllListVo vo : voList) {
         IdVo idVo = new IdVo();
         idVo.setFromId(vo.getToId());
         myFriendList.add(idVo);
      }

      //根据所有好友列表去查询每个人的好友名单以及每个人好友的资料信息
      //随机取10条   该信息资料就是可能认识的人
      List<FriendAllQueryBo> boList = baseMapper.listAllTwoFriend(myFriendList);
      //根据好友的好友名单装数据返回给前端

      if (null == boList || boList.size() == 0) {
         return null;
      }

      Map<Long, FriendAllListVo> map = voList.stream().
            collect(Collectors.toMap(FriendAllListVo::getToId, friendAllListVo
                  -> friendAllListVo));

      return boList.stream().map(friendAllQueryBo -> {
         FriendBo bo = new FriendBo();
         BeanUtils.copyProperties(friendAllQueryBo,bo);
         FriendToWithBo friendToWithBo = new FriendToWithBo();
         friendToWithBo.setHeard(map.get(friendAllQueryBo.getFromId()).getHeadUrl());
         friendToWithBo.setWeChatName(map.get(friendAllQueryBo.getFromId()).getNickName());
         //将共同好友加入对象中返回
         bo.setBo(friendToWithBo);

         return bo;
      }).collect(Collectors.toList());
   }

   @Override
   public void getOntOnlineInfo() {

      UserLoginQuery user = localUser.getUser();
      List<FriendNotOnlineVo> list = friendNotOnlineService.queryOne(user.getId());

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
            rabbitTemplate.convertAndSend("game_msg_fanoutExchange", "", JSON.toJSONString(msgInfo));
         }

         //删除数据库的信息
         friendNotOnlineService.deleteLook(longList);
      }

   }
}
