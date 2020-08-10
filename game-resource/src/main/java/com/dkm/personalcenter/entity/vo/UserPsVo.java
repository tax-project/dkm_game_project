package com.dkm.personalcenter.entity.vo;

import com.dkm.personalcenter.entity.bo.PsBottleBo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: game_project
 * @description: 用户体力 、 体力瓶信息
 * @author: zhd
 * @create: 2020-08-10 10:09
 **/
@ApiModel("用户体力、体力瓶信息")
@Data
public class UserPsVo {
    @ApiModelProperty("用户当前体力")
    private Integer ps;
    @ApiModelProperty("用户总体力")
    private Integer psAll;
    @ApiModelProperty("体力瓶信息")
    private List<PsBottleBo> PsBottleBo;
}
