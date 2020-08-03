package com.dkm.box.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @program: game_project
 * @description: 自动出售装备等级信息
 * @author: zhd
 * @create: 2020-08-03 09:36
 **/
@Data
public class AutoSellEntity {
    @TableId
    private Long userId;
    private String autoSellOrder;
}
