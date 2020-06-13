package com.dkm.seed.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/8 11:36
 */
@Data
@TableName("tb_seeds_fall")
public class SeedsFall extends Model<SeedsFall> {
    /**
     *种子详情id
     */
    private Long id;
    /**
     * 种子id
     */
    private Long seedId;
    /**
     * 掉落的金币
     */
    private Integer dropCoins=0;
    /**
     * 掉落的红包
     */
    private Double dropRedEnvelope=0.0;
}
