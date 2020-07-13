package com.dkm.diggings.bean.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author OpenE
 */
@Data
@TableName("tb_diggings_history")
public class DiggingsHistoryEntity {
    @TableId
    private long id;
    private long userId;
    private long familyId;
    private LocalDateTime startDate;
    private LocalDateTime stopDate;
    private long integral;
    private int mineItemLevel;
    private boolean settled;
}
