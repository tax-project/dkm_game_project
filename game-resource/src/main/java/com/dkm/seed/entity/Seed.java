package com.dkm.seed.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @USER: 刘梦祺
 * @DATE: 2020/5/10 11:51
 */
@Data
@TableName("tb_seed")
public class Seed {
    /**
     * 种子id
     */
    private Integer seedId;
    /**
     * 种子名称
     */
    private String seedName;
    /**
     * 种子等级
     */
    private Integer seedGrade;
    /**
     * 种子成熟时间
     */
    private LocalDateTime seedGrowuptime;
    /**
     *种子产出的金币
     */
    private Integer seedProdgold;
    /**
     *
     */
    private Integer seedProdred;
    /**
     *种子产出的经验
     */
    private Integer seedExperience;
    /**
     *种子种植金币
     */
    private Integer seedGold;
    /**
     *
     */
    private String exp1;
    /**
     *
     */
    private String exp2;
    /**
     *
     */
    private String exp3;
    /**
     *种子图片
     */
    private String seedImg;



}
