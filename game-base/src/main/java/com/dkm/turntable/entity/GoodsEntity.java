package com.dkm.turntable.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @program: game_project
 * @description: 物品
 * @author: zhd
 * @create: 2020-06-23 10:28
 **/
@Data
@TableName("tb_goods")
public class GoodsEntity {

    @TableId
    private Long id;

    private String name;

    private String url;
    private Integer goodType;
    private String goodContent;
    private Integer goodMoney;
    private String exp1;
    private String exp2;
    private String tabUrl;
}
