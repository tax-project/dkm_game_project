package com.dkm.turntable.entity.bo;

import lombok.Data;

/**
 * @Author: HuangJie
 * @Date: 2020/5/14 9:42
 * @Version: 1.0V
 */
@Data
public class TurntableItemBO {
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
