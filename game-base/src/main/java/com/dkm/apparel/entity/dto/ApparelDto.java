package com.dkm.apparel.entity.dto;

import com.dkm.apparel.entity.ApparelEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @program: game_project
 * @description: 服饰dto
 * @author: zhd
 * @create: 2020-05-22 09:42
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ApparelDto extends ApparelEntity {
    /**
     * apparelUserId
     */
    private Long apparelUserId;

    /**
     * 完成时间
     */
    private String apparelCompleteTime;

    /**
     * 消耗钻石
     */
    private Integer diamond;
    /**
     * 状态
     */
    private Integer isEquip;
}
