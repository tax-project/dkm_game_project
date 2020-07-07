package com.dkm.userInfo.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.entity.bo.UserInfoSkillBo;
import com.dkm.entity.bo.UserPlunderBo;
import com.dkm.entity.vo.AttendantWithUserVo;
import com.dkm.entity.vo.IdVo;
import com.dkm.entity.vo.OpponentVo;
import com.dkm.userInfo.entity.UserInfo;
import com.dkm.userInfo.entity.bo.IncreaseUserInfoBO;
import com.dkm.userInfo.entity.bo.ReputationRankingBO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qf
 * @date 2020/5/11
 * @vesion 1.0
 **/
@Component
public interface UserInfoMapper extends IBaseMapper<UserInfo> {

    /**
     * 增加用户金币、钻石、声望、经验
     * @param increaseUserInfoBO 参数对象
     * @return 是否添加成功
     */
    Integer increaseUserInfo(IncreaseUserInfoBO increaseUserInfoBO);

    /**
     * 减少用户金币、钻石、声望
     * @param increaseUserInfoBO 参数对象
     * @return 是否成功
     */
    Integer cutUserInfo(IncreaseUserInfoBO increaseUserInfoBO);

    /**
     *  随机返回20条用户信息
     *  掠夺需要
     * @return 返回掠夺信息结果
     */
    List<UserPlunderBo> listUserPlunder();

    /**
     *  随机返回9条跟班信息
     * @param userId 用户
     * @return 返回结果
     */
    List<AttendantWithUserVo> listAttUser(Long userId);

    /**
     *  掠夺减少体力
     * @param userId 用户Id
     * @param grade 等级
     * @return 返回结果
     */
    Integer updateStrength(@Param("userId") Long userId, @Param("grade") Integer grade);

    /**
     *  升级技能
     *  减少消耗的金币
     *  增加声望
     * @param bo 用户id 金币  声望
     * @return 返回结果
     */
    Integer updateInfo(UserInfoSkillBo bo);

    /**
     * 获取用户声望排行榜
     * @return 排行集合
     */
    List<ReputationRankingBO> reputationRanking();

    /**
     *  查询所有对手用户信息
     * @param list
     * @return
     */
    List<OpponentVo> listOpponent(List<IdVo> list);

}
