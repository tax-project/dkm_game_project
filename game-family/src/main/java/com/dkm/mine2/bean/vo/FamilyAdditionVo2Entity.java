package com.dkm.mine2.bean.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("家族的等级加成")
@Data
@TableName("tb_mine_family_level_addition")
public class FamilyAdditionVo2Entity {
    @ApiModelProperty("家族等级")
    @TableId
    private int familyLevel;
    @ApiModelProperty("家族等级对应的段位名")
    private String levelName;
    @ApiModelProperty("家族等级对应的金币加成")
    private Double addition;
}
