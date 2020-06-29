package com.dkm.goldMine.bean.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("tb_family_mines_user")
public class MineUserEntity {
    @TableId
    private Long userId;
    private Long fightSize;
    private LocalDateTime lastFightDate;
}
