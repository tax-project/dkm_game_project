package com.dkm.gift.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @program: game_project
 * @description: 送礼信息
 * @author: zhd
 * @create: 2020-05-29 09:24
 **/
@Data
@AllArgsConstructor
public class SendGiftVo {
    /**
     * 送礼人userId
     */
    private Long sendId;
    /**
     * 收礼人id
     */
    private Long receiveId;
    /**
     * 消耗金币
     */
    @Builder.Default
    private Integer gold=0;
    /**
     * 消耗钻石
     */
    @Builder.Default
    private Integer diamond=0;
    /**
     * 魅力值
     */
    private Integer charm;
    /**
     * 礼品id
     */
    private Long giftId;
    /**
     * 礼品名称
     */
    private String giftName;
    /**
     * 礼品数量
     */
    private Integer giftNumber;
}
