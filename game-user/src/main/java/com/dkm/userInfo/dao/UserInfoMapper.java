package com.dkm.userInfo.dao;

import com.dkm.IBaseMapper.IBaseMapper;
import com.dkm.entity.bo.UserPlunderBo;
import com.dkm.userInfo.entity.UserInfo;
import com.dkm.userInfo.entity.bo.IncreaseUserInfoBO;
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

}
