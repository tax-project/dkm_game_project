package com.dkm.feedback.service;

import com.dkm.feedback.entity.FeedBack;

import java.util.List;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/19 14:19
 */
public interface IFeedBackService {

    /**
     * 添加反馈信息
     */
     int insertFeedBack(FeedBack feedBack);

    /**
     * 查询自己反馈的信息
     */
    List<FeedBack> queryAllFeedBack();
}
