package com.dkm.seed.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: dkm_game
 * @DESCRIPTION:
 * @USER: 刘梦祺
 * @DATE: 2020/5/12 19:39
 */
@Data
@TableName("tb_plant_unlock")
public class SeedUnlock {
    @TableId
    private long puId;
    private long seedId;
    private long userId;
    /**
     * 当前解锁进度
     */
    private Integer seedPresentUnlock=0;
    private Integer seedAllUnlock;
    private Integer seedStatus=0;

}
