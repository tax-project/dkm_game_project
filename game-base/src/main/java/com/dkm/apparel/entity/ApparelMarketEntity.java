package com.dkm.apparel.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: game_project
 * @description:
 * @author: zhd
 * @create: 2020-06-19 15:23
 **/
@Data
@TableName("tb_apparel_market")
public class ApparelMarketEntity {

    private Long apparelMarketId;

    private Long userId;

    private Long apparelDetailId;

    private LocalDateTime maturityTime;

    private Integer gold;
}
