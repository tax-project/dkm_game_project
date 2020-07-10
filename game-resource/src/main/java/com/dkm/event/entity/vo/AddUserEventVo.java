package com.dkm.event.entity.vo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @author MQ
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/7/10 13:38
 */
@Data
public class AddUserEventVo extends Model<AddUserEventVo> {
    /**
     * 抢夺方用户id
     */
    public Long heUserId;

    /**
     * 事件内容
     */
    public String toRobContent;
}
