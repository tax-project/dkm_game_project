package com.dkm.friend.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.friend.entity.FriendRequest;
import com.dkm.friend.entity.vo.FriendRequestInfoVo;
import com.dkm.friend.entity.vo.IdVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/11
 * @vesion 1.0
 **/
@Component
public interface FriendRequestMapper extends IBaseMapper<FriendRequest> {


   /**
    * 根据id查询所有加我的记录
    * @param list
    * @return
    */
   List<FriendRequestInfoVo> listAllRequestFriend(List<IdVo> list);
}
