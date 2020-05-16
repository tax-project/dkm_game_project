package com.dkm.friend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkm.constanct.CodeType;
import com.dkm.exception.ApplicationException;
import com.dkm.friend.dao.FriendNotOnlineMapper;
import com.dkm.friend.entity.FriendNotOnline;
import com.dkm.friend.entity.vo.FriendNotOnlineVo;
import com.dkm.friend.service.IFriendNotOnlineService;
import com.dkm.utils.DateUtil;
import com.dkm.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author qf
 * @date 2020/5/11
 * @vesion 1.0
 **/
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class FriendNotOnlineServiceImpl extends ServiceImpl<FriendNotOnlineMapper, FriendNotOnline> implements IFriendNotOnlineService {


   @Autowired
   private IdGenerator idGenerator;

   /**
    * 查询该登录用户所有未在线消息
    * @param userId
    * @return
    */
   @Override
   public List<FriendNotOnlineVo> queryOne(Long userId) {

      LambdaQueryWrapper<FriendNotOnline> wrapper = new LambdaQueryWrapper<FriendNotOnline>()
            .eq(FriendNotOnline::getToId,userId)
            .eq(FriendNotOnline::getIsLook,0);

      List<FriendNotOnline> list = baseMapper.selectList(wrapper);


      if (null != list && list.size() == 0) {
         return null;
      }

      List<FriendNotOnlineVo> result = new ArrayList<>();

      for (FriendNotOnline online : list) {
         FriendNotOnlineVo vo = new FriendNotOnlineVo();
         vo.setFromId(online.getFromId());
         vo.setToId(online.getToId());
         vo.setContent(online.getContent());
         vo.setType(online.getType());
         vo.setCreateDate(DateUtil.formatDateTime(online.getCreateDate()));
         result.add(vo);
      }

      return result;
   }


   /**
    * 增加未在线消息
    * @param vo
    */
   @Override
   public void insertNotOnline(FriendNotOnlineVo vo) {
      FriendNotOnline friendNotOnline = new FriendNotOnline();

      friendNotOnline.setId(idGenerator.getNumberId());
      friendNotOnline.setFromId(vo.getFromId());
      friendNotOnline.setToId(vo.getToId());
      friendNotOnline.setContent(vo.getContent());
      friendNotOnline.setCreateDate(LocalDateTime.now());
      friendNotOnline.setType(vo.getType());
      friendNotOnline.setIsLook(0);

      int insert = baseMapper.insert(friendNotOnline);

      if (insert <= 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "增加失败");
      }
   }

   @Override
   public void updateLook(List<Long> list) {
      Integer integer = baseMapper.updateLook(list);

      if (integer <= 0) {
         throw new ApplicationException(CodeType.SERVICE_ERROR, "修改出错");
      }
   }


   /**
    * 删除所有已读的消息
    */
   @Override
   public void deleteLook() {
      LambdaQueryWrapper<FriendNotOnline> wrapper = new LambdaQueryWrapper<FriendNotOnline>()
            .eq(FriendNotOnline::getIsLook,1);

      baseMapper.delete(wrapper);
   }
}
