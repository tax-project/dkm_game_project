package com.dkm.userInfo.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.entity.bo.UserInfoSkillBo;
import com.dkm.entity.bo.UserPlunderBo;
import com.dkm.userInfo.entity.UserInfo;
import com.dkm.userInfo.entity.bo.IncreaseUserInfoBO;
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
     * 增加用户金币、钻石、声望
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

}
