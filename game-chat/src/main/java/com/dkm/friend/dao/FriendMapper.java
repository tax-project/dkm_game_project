package com.dkm.friend.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.friend.entity.Friend;
import com.dkm.friend.entity.vo.FriendAllListVo;
import com.dkm.friend.entity.vo.IdVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/11
 * @vesion 1.0
 **/
@Component
public interface FriendMapper extends IBaseMapper<Friend> {


   /**
    * 查询当前登陆人所有好友
    * @param list id的集合
    * @return 好友信息的集合
    */
   List<FriendAllListVo> queryAllFriend(List<IdVo> list);
}
