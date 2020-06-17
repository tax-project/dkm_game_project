package com.dkm.goldMine.bean.ao;

import lombok.Data;

/**
 * @Author: HuangJie
 * @Date: 2020/5/16 15:33
 * @Version: 1.0V
 */
@Data
public class UserInfoBO {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 金币
     */
    private Integer userInfoGold;
    /**
     * 声望
     */
    private Integer userInfoRenown;
    /**
     * 钻石
     */
    private Integer userInfoDiamonds;


}
