package com.dkm.wechat.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

/**
 * @Author: HuangJie
 * @Date: 2020/5/9 10:05
 * @Version: 1.0V
 */
@Data
@TableName("tb_user")
public class User {
    /**
     *  用户ID
     */
    @TableId
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
     * 账号是否有效，默认为0，有效
     * 1--无效
     */
    private Integer userIsEffective;

    /**
     * 备注字段
     * 暂存用户密码
     */
    private String userRemark;

    /**
     * 性别
     * 1--男
     * 2--女
     */
    private Integer userSex;

    /**
     *  年龄
     */
    private LocalDate userAge;

    /**
     * 个性签名
     */
    private String userSign;

    /**
     * 个人说明
     */
    private String userExplain;

    /**
     * 个人二维码
     */
    private String qrCode;

    /**
     *  用户地区
     *  国家-省-市
     */
    private String userAddress;
}
