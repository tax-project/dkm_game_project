package com.dkm.event.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/6/10 14:03
 */
@Data
@TableName("tb_user_event_content")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class UserEventContent extends Model<UserEventContent> {

    /**
     * 他人的用户id
     */
    private Long heUserId;

    /**
     * 时间
     */
    private LocalDateTime toRobTime;

    /**
     * 事件内容
     */
    private String toRobContent;

    private String time;
}
