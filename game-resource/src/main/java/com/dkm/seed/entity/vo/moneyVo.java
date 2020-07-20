package com.dkm.seed.entity.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author MQ
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/7/14 16:22
 */
@Data
public class moneyVo {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 种子首次产出的金钱
     */
    private Double seedProdred;

    /**
     * 成熟时间
     */
    private LocalDateTime plantTime;

    /**
     * 主键id
     */
    private Long Id;
}
