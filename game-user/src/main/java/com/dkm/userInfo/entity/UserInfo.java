package com.dkm.userInfo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * @author qf
 * @date 2020/5/11
 * @vesion 1.0
 **/
@Data
@TableName("tb_user_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class UserInfo extends Model<UserInfo> {

   /**
    * 主键
    */
   @TableId
   private Long userInfoId;

   private Long userId;

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
   private LocalDate userInfoEnvelopeTime;

   /**
    * 可以领红包的总次数
    * 初始值==15
    */
   private Integer userInfoAllEnvelopeMuch;

}
