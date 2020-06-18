package com.dkm.union.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: game_project
 * @description: 工会
 * @author: zhd
 * @create: 2020-06-18 10:44
 **/
@Data
public class UnionEntity {

    @TableId
    private Long unionId;
    /**
     * 会长
     */
    private Long userId;
    /**
     * 工会名字
     */
    private String unionName;
    /**
     * 两个副会长
     */
    private Long unionUserId1;
    private Long unionUserId2;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
