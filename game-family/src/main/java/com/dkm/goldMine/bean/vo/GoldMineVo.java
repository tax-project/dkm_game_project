package com.dkm.goldMine.bean.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 家族金矿ID
 */
@Data
public class GoldMineVo {
    long familyId;
    long goldMineId;
    long familyLevel = 0;
    final List<MineItemVo> goldItems = new ArrayList<>();
}
