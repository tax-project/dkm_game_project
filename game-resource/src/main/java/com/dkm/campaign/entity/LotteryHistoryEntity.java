package com.dkm.campaign.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("tb_lottery_last_history")
@Data
public class LotteryHistoryEntity {
    @TableId
    private Long id;
    private Long userId;
    private String prizeText;
    private Long diamonds;
}
