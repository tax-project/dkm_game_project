package com.dkm.entity.bo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author qf
 * @date 2020/5/18
 * @vesion 1.0
 **/
@Data
public class UserInfoQueryBo {

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
    * 年龄
    */
   private Long age;

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


   private Long userInfoId;

   /**
    * 用户等级
    * 初始值==1
    */
   private Integer userInfoGrade;

   /**
    * 用户金币数目
    * 初始值==10000
    */
   private Integer userInfoGold;

   /**
    * 声望
    * 初始值==500
    */
   private Integer userInfoRenown;

   /**
    * 钻石数量
    * 初始值==0
    */
   private Integer userInfoDiamonds;

   /**
    * 0--默认  不是VIP
    * 1--是VIP
    * 初始值==0
    */
   private Integer userInfoIsVip;

   /**
    * 用户积分数量
    * 初始值==0
    */
   private Integer userInfoIntegral;

   /**
    * 用户当前体力
    * 初始值==200
    */
   private Integer userInfoStrength;

   /**
    * 用户总体力
    * 初始值==200
    */
   private Integer userInfoAllStrength;

   /**
    * 用户当前等级现有经验值
    * 初始值==0
    */
   private Long userInfoNowExperience;

   /**
    * 用户下一等级所需经验值
    * 初始值==600
    */
   private Long userInfoNextExperience;

   /**
    * 用户红包可用余额
    * 初始值==0
    */
   private Double userInfoPacketBalance;

   /**
    * 已经领取红包的次数
    * 初始值==0
    */
   private Integer userInfoEnvelopeMuch;

   /**
    * 红包最后领取时间
    */
   private String userInfoEnvelopeQueryTime;

   /**
    * 可以领红包的总次数
    * 初始值==15
    */
   private Integer userInfoAllEnvelopeMuch;

   /**
    * 用户技能状态
    * 1--技能初始化完毕
    */
   private Integer userInfoSkillStatus;
}
