package com.dkm.userInfo.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.userInfo.entity.UserInfo;
import com.dkm.userInfo.entity.bo.IncreaseUserInfoBO;
import org.springframework.stereotype.Component;

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

}
