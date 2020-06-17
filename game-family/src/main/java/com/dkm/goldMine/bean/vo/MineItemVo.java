package com.dkm.goldMine.bean.vo;

import lombok.Data;

@Data
public class MineItemVo {
    int index;
    Long goldItemId;
    boolean privateItem;
    int level;
    int locationX;
    int locationY;
    /**
     * 被占领
     */
    boolean occupied;


}

//Profit per hour