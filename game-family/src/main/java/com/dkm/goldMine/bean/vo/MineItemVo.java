package com.dkm.goldMine.bean.vo;

import lombok.Data;

@Data
public class MineItemVo {
    private int index;
    private Long goldItemId;
    private boolean privateItem;
    private int level;
    /**
     * 被占领
     */
    private boolean occupied;


}

//Profit per hour