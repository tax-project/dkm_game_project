package com.dkm.seed.entity.vo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @author MQ
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/29 21:00
 */
@Data
public class SeedsFallVo extends Model<SeedsFallVo> {

    /**
     * 掉落的金币
     */
    private Integer dropCoins=0;

    /**
     * 掉落的红包
     */
    private Double dropRedEnvelope=0.0;

    /**
     * 掉落的花
     */
    private Integer dropFallingFlowers;
}
