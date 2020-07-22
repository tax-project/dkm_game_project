package com.dkm.backpack.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: game_project
 * @description: 用户背包物品信息
 * @author: zhd
 * @create: 2020-07-17 21:42
 **/
@Data
@ApiModel("用户背包物品信息")
public class UserBackpackGoodsVo{
    @ApiModelProperty("背包id")
    private Long backpackId;
    @ApiModelProperty("物品数量")
    private Integer number;
    @ApiModelProperty("物品详情")
    private String goodContent;
    @ApiModelProperty("物品名称")
    private String name;
    @ApiModelProperty("物品图片")
    private String url;
    @ApiModelProperty("物品类型  (1-装备)（2 - 道具） （3 - 食物）")
    private Integer goodType;
}
