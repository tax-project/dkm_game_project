package com.dkm.seed.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 刘梦祺
 * @PROJECT_NAME: dkm_game
 * @DESCRIPTION:
 * @USER: 刘梦祺
 * @DATE: 2020/5/12 11:00
 */
@Data
@TableName("tb_land_seed")
public class LandSeed {

  private long leId;
  private long userId;
  private long seedId;
  private long laId;
  private long grade;
  private LocalDateTime plantTime;


}
