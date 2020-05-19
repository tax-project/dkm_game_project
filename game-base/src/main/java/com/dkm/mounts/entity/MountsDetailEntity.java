package com.dkm.mounts.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhd
 * @date 2020/5/11 17:20
 */
@Data
@TableName("tb_mounts_detail")
public class MountsDetailEntity {
    @TableId
    private Long mountsId;

    /**
     * 座驾名称
     */
    private String mountsName;

    /**
     * 座驾金币
     */
    private Integer mountsGold;

    /**
     * 座驾图片
     */
    private String mountsImage;

    /**
     * 钻石
     */
    private Integer mountsDiamond;
    /**
     * 1 金币车 2 钻石车
     */
    private Integer mountsType;
}
