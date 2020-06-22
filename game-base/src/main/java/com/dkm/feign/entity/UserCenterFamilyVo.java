package com.dkm.feign.entity;

import lombok.Data;

import java.util.List;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-06-22 16:17
 **/
@Data
public class UserCenterFamilyVo {
    private List<String> imgs;
    private Long familyId;
    private String familyName;
    private Integer familyPopularity = 66;
}
