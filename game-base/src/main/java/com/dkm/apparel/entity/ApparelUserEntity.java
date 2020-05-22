package com.dkm.apparel.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: game_project
 * @description: 用户服饰关联
 * @author: zhd
 * @create: 2020-05-22 10:28
 **/
@Data
@TableName("tn_apparel_user")
public class ApparelUserEntity {
    @TableId
    private Long apparelUserId;
    /**
     * userId
     */
    private  Long userId;
    /**
     * 服饰详情id
     */
    private Long apparelDetailId;
    /**
     * 完成时间
     */
    private LocalDateTime apparelCompleteTime;
}
