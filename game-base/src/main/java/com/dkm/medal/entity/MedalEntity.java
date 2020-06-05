package com.dkm.medal.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @program: game_project
 * @description: 勋章表
 * @author: zhd
 * @create: 2020-06-05 10:11
 **/
@Data
@TableName("tb_medal")
public class MedalEntity {

    @TableId
    private Long medalId;
    /**
     * 所属礼物id
     */
    private Long giId;
    /**
     * 勋章名称
     */
    private String medalName;

    /**
     * 勋章类型0礼物1幸运
     */
    private Integer medalType;
    /**
     * 等级1所需进度
     */
    private Integer medalProcess1;
    /**
     * 等级2所需进度
     */
    private Integer medalProcess2;
    /**
     * 等级3所需进度
     */
    private Integer medalProcess3;
    /**
     * 等级1奖励
     */
    private Integer medalLevelReward1;
    /**
     * 等级2奖励
     */
    private Integer medalLevelReward2;
    /**
     * 等级3奖励
     */
    private Integer medalLevelReward3;
}
