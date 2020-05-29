package com.dkm.gift.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @program: game_project
 * @description: 礼物
 * @author: zhd
 * @create: 2020-05-27 09:22
 **/
@Data
@TableName("tb_gift")
public class GiftEntity {
    @TableId
    private Long giId;

    /**
     * 0金币 1钻石
     */
    private Integer giType;
    /**
     * 名称
     */
    private String giName;
    /**
     * 金币
     */
    private Integer giGold;
    /**
     * 魅力
     */
    private Integer giCharm;
    /**
     * 钻石
     */
    private Integer giDiamond;
    /**
     * 图标
     */
    private String giUrl;
}
