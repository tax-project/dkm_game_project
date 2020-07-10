package com.dkm.userInfo.service;

import com.dkm.entity.bo.UserInfoSkillBo;
import com.dkm.entity.bo.UserPlunderBo;
import com.dkm.entity.vo.AttendantWithUserVo;
import com.dkm.entity.vo.IdVo;
import com.dkm.entity.vo.OpponentVo;
import com.dkm.userInfo.entity.bo.IncreaseUserInfoBO;
import com.dkm.userInfo.entity.bo.ReputationRankingBO;
import com.dkm.wechat.entity.vo.WeChatUserInfoVo;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/11
 * @vesion 1.0
 **/
public interface IUserInfoService {

   /**
    * 增加用户详细信息
    * 初始化用户信息
    * @param userId
    */
   void insertUserInfo (Long userId);

   /**
    * 修改红包总次数
    * @param much 次数
    * @param userId 用户id
    * @param userInfoDiamonds 钻石
    */
   void updateUserInfo (Integer much, Long userId,Integer userInfoDiamonds);

   /**
    * 增加用户金币、钻石、声望
    * @param increaseUserInfoBO 参数对象
    */
   void increaseUserInfo(IncreaseUserInfoBO increaseUserInfoBO);

   /**
    * 减少用户金币、钻石、声望
    * @param increaseUserInfoBO 参数对象
    */
   void cutUserInfo(IncreaseUserInfoBO increaseUserInfoBO);

   /**
    *  随机返回20条用户信息
    *  掠夺需要
    * @return 返回掠夺信息结果
    */
   List<UserPlunderBo> listUserPlunder ();

   /**
    *  掠夺减少体力
    * @param userId 用户id
    * @param grade 等级
    */
   void updateStrength (Long userId, Integer grade);

   /**
    *  技能升级
    *  消耗金币增加声望
    *  技能模块
    * @param bo 参数
    */
   void updateInfo (UserInfoSkillBo bo);

   /**
    * 查询所有跟班的用户信息
    * 随机返回9条
    * @param userId
    * @return
    */
   List<AttendantWithUserVo> listAttUser (Long userId);


   /**
    * 获取声望排行
    * @return 排行结果
    */
   List<ReputationRankingBO> reputationRanking();

   /**
    *  查询所有对手的用户信息
    * @param list
    * @return
    */
   List<OpponentVo> listOpponent (List<IdVo> list);

   /**
    *  查询个人信息
    *  个人信息页面的接口
    * @return 返回个人信息
    */
   WeChatUserInfoVo queryWeChatUserInfo ();
}