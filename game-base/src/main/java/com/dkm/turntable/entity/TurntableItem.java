package com.dkm.turntable.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: HuangJie
 * @Date: 2020/5/14 9:25
 * @Version: 1.0V
 */
@Data
@TableName("tb_turntable_item")
public class TurntableItem {
    /**
     * 转盘槽的物品ID
     */
    @TableId("turntable_item_id")
    private Long turntableItemId;
    /**
     * 物品名称
     */
    private String turntableItemName;
    /**
     * 物品稀有程度
     */
    private Integer turntableItemRare;
    /**
     * 物品的图片地址
     */
    private String turntableItemImageUrl;
}
