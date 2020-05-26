package com.dkm.family.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: game_project
 * @description: 家族
 * @author: zhd
 * @create: 2020-05-20 09:48
 **/
@Data
@TableName("tb_family")
public class FamilyEntity {
    @TableId
    private Long familyId;
    /**
     * 家族名称
     */
    private String familyName;
    /**
     * 家族等级
     */
    private Integer familyGrade;
    /**
     * 家族创建时间
     */
    private LocalDateTime familyCreateTime;
    /**
     * 家族二维码
     */
    private String familyQrcode;
    /**
     * 家族介绍
     */
    private String familyIntroduce;
    /**
     * 家族欢迎词
     */
    private String familyWelcomeWords;
    /**
     * 加入方式
     */
    private Integer familyJoin;
}
