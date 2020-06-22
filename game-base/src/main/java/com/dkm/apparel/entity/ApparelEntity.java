package com.dkm.apparel.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @program: game_project
 * @description: 服饰
 * @author: zhd
 * @create: 2020-05-15 13:58
 **/
@Data
@TableName("tb_apparel_detail")
public class ApparelEntity {
    @TableId
    private Long apparelId;
    /**
     * 地址
     */
    private String apparelUrl;
    /**
     * 名称
     */
    private String apparelName;
    /**
     * 类型
     */
    private Integer apparelType;
    /**
     * 金币
     */
    private Integer apparelGold;
    /**
     * 布料
     */
    private Integer apparelFabric;
    /**
     * 0 女 1男
     */
    private Integer apparelSex;
}
