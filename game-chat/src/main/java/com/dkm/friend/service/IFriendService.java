package com.dkm.friend.service;

import com.dkm.friend.entity.vo.FriendAllListVo;
import com.dkm.friend.entity.vo.FriendVo;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/11
 * @vesion 1.0
 **/
public interface IFriendService {

   /**
    * 成为好友
    * @param vo
    */
   void insertFriend(FriendVo vo);

   /**
    *  删除好友
    * @param toId 要删除的人的id
    */
   void deleteFriend(Long toId);

   /**
    * 展示全部好友
    * @return 所有好友信息
    */
   List<FriendAllListVo> listAllFriend();
}
