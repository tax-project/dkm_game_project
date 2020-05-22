package com.dkm.family.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @program: game_project
 * @description: 家族详情
 * @author: zhd
 * @create: 2020-05-20 09:48
 **/
@Data
@TableName("tb_family_details")
public class FamilyDetailEntity {
    @TableId
    private Long familyDetailsId;
    /**
     * 家族id
     */
    private Long familyId;
    /**
     * userId
     */
    private Long userId;
    /**
     * 用户管理员权限
     */
    private Integer isAdmin;
    private String exp1;
}
