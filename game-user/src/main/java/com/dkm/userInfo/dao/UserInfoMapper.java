package com.dkm.userInfo.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.userInfo.entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * @author qf
 * @date 2020/5/11
 * @vesion 1.0
 **/
@Component
public interface UserInfoMapper extends IBaseMapper<UserInfo> {

    /**
     * 增加用户金币
     * @param goldNumber 增加数目
     * @param userId 用户ID
     * @return 是否添加成功
     */
    @Update("update tb_user_info set user_info_gold=user_info_gold+#{goldNumber} where user_id = #{userId}")
    Integer increaseUserInfoGold(@Param("goldNumber") Double goldNumber, @Param("userId") Long userId);

}
