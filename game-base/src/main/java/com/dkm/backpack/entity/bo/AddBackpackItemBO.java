package com.dkm.backpack.entity.bo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: HuangJie
 * @Date: 2020/5/15 10:39
 * @Version: 1.0V
 */
@Data
public class AddBackpackItemBO {
    /**
     * 物品ID
     */
    private Long itemId;
    /**
     * 物品所在表编号
     */
    private Integer itemTableNumber;
    /**
     * 物品数量
     */
    private Integer itemNumber;
}
