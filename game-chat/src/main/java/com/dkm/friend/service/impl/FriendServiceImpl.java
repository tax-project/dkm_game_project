package com.dkm.friend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.friend.dao.FriendMapper;
import com.dkm.friend.entity.Friend;
import com.dkm.friend.entity.vo.FriendAllListVo;
import com.dkm.friend.entity.vo.FriendVo;
import com.dkm.friend.entity.vo.IdVo;
import com.dkm.friend.service.IFriendRequestService;
import com.dkm.friend.service.IFriendService;
import com.dkm.jwt.contain.LocalUser;
import com.dkm.jwt.entity.UserLoginQuery;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

      List<IdVo> list = new ArrayList<>();
      for (Friend friend : friendList) {
         IdVo vo = new IdVo();
         vo.setFromId(friend.getToId());
         list.add(vo);
      }

      //查询所有好友的信息
      return baseMapper.queryAllFriend(list);
   }
}
