package com.dkm.feedback.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/19 14:15
 */
@Data
@TableName("tb_feedback_information")
public class FeedBack extends Model<FeedBack> {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     *问题类型（1常见问题，2充值问题，3产品问题，4意见与反馈，5实名认证问题，6其他问题）
     */
    private Integer fStatus;

    /**
     * 反馈内容
     */
    private String fContent;

    /**
     * 回复状态（0 待回复  1未解决，2已解决）
     */
    private Integer replyStatus;



}
