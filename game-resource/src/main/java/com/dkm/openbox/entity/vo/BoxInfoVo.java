package com.dkm.openbox.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-07-17 14:31
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("宝箱信息")
public class BoxInfoVo {
    /**
     * 宝箱id
     */
    @ApiModelProperty("宝箱id 开箱时传过来")
    private Long boxId;
    @JsonIgnore
    private LocalDateTime openTime;
    /**
     * 开启时间
     */
    @ApiModelProperty("开箱时间 status为0时展示")
    private String time;
    /**
     * 宝箱等级
     */
    @ApiModelProperty("宝箱等级")
    private Integer boxLevel;
    /**
     * 是否能开启
     */
    @ApiModelProperty("宝箱状态，0不可开展示时间 1可开启")
    private Integer status;

}
