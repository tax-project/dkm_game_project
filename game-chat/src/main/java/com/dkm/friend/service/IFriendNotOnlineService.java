package com.dkm.friend.service;

import com.dkm.friend.entity.vo.FriendNotOnlineVo;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/11
 * @vesion 1.0
 **/
public interface IFriendNotOnlineService {

   /**
    * 查询数据数据
    * @param userId
    * @return
    */
   List<FriendNotOnlineVo> queryOne(Long userId);


   /**
    * 增加未在线消息
    * @param vo
    */
   void insertNotOnline(FriendNotOnlineVo vo);

   /**
    *  更改未读状态
    * @param list id集合
    */
   void updateLook(List<Long> list);

   /**
    * 删除所有已读的消息
    */
   void deleteLook();
}
