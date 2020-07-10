package com.dkm.family.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @program: game_project
 * @description: 最近访问家族
 * @author: zhd
 * @create: 2020-07-10 14:20
 **/
@Data
@TableName("tb_family_lately")
public class FamilyLatelyEntity {
    @TableId
    private Long id;
    private Long familyId;
    private Long userId;
}
