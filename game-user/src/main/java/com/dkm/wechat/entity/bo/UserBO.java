package com.dkm.wechat.entity.bo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
/**
 * @Author: HuangJie
 * @Date: 2020/5/9 11:08
 * @Version: 1.0V
 */
@Data
@TableName("tb_user")
public class UserBO {
    /**
     *  用户ID
     */
    private Long userId;
    /**
     * 微信OpenId
     */
    private String weChatOpenId;
    /**
     * 微信昵称
     */
    private String weChatNickName;
    /**
     * 微信头像地址
     */
    private String weChatHeadImgUrl;
    /**
     * 用户身份证号码
     */
    private String userIdCard;
    /**
     * 用户手机号码
     */
    private String userPhone;
    /**
     * 用户等级
     */
    private Integer userGrade;
    /**
     * 用户金币
     */
    private Integer userGold;
    /**
     * 用户红包余额
     */
    private Double userPacketBalance;
    /**
     * 用户声望值
     */
    private Integer userRenown;
    /**
     * 是否为VIP,默认为0，不是VIP
     */
    private Integer userIsVip;
    /**
     * 账号是否有效，默认为0，有效
     */
    private Integer userIsEffective;

    /**
     * 备注字段
     * 暂存用户密码
     */
    private String userRemark;
}
