package com.dkm.seed.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author MQ
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/7/1 16:11
 */
@Data
@TableName("tb_pirate")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class Pirate extends Model<Pirate> {

    /**
     * 被盗主键
     */
    private Long pirateId;

    /**
     * 盗方userId
     */
    private Long pirateToId;

    /**
     * 被盗方userId
     */
    private Long pirateFromId;

    /**
     *被盗金币
     */
    private Integer pirateLossGold;

    /**
     * 被盗红包
     */
    private BigDecimal pirateLossRed;

    /**
     * 被盗时间
     */
    private LocalDateTime pirateLossTime;

}
