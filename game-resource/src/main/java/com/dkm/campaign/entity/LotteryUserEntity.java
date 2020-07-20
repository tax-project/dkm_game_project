package com.dkm.campaign.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("tb_lottery_user")
@Data
public class LotteryUserEntity {
    @TableId
    private Long id;
    private Long tbLotteryId;
    private Long userId;

}
