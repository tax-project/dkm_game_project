package com.dkm.discount.bean.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

/**
 * @author fkl
 */
@TableName("tb_lottery")
public class LotteryEntity {
    @TableId
    private Long id;
    private Long cycleSize;
    private Long duration;
    private LocalDateTime startDate;
    private LocalDateTime stopDate;

}
