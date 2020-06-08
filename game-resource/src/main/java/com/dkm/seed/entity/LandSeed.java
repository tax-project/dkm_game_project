package com.dkm.seed.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
public class LandSeed extends Model<LandSeed>{

  private Long id;

  private Long userId;
  private Long seedId;
  private Integer laNo;
  private LocalDateTime plantTime;
  private Integer leStatus;


}
