package com.dkm.union.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: game_project
 * @description: 工会
 * @author: zhd
 * @create: 2020-06-18 10:44
 **/
@Data
@TableName("tb_union_info")
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
    private Long viceUserId1;
    private Long viceUserId2;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
