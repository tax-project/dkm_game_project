package com.dkm.feedback.entity.vo;

import lombok.Data;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/19 15:54
 */
@Data
public class FeedBackVo {

    /**
     *问题类型（1常见问题，2充值问题，3产品问题，4意见与反馈，5实名认证问题，6其他问题）
     */
    public Integer fStatus;

    /**
     * 反馈内容
     */
    public String fContent;
}
