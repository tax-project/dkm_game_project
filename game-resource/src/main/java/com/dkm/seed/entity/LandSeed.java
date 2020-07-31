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
  /**
   * 用户土地id
   */
  private Long id;

  /**
   * 用户id
   */
  private Long userId;

  /**
   * 种子id
   */
  private Long seedId;

  /**
   * 土地编号
   */
  private Integer laNo;

  /**
   * 种子成熟时间
   */
  private LocalDateTime plantTime;

  /**
   * 种植或收取状态  1已种植  2待收取 3已收取
   */
  private Integer leStatus;

  /**
   * 是否新种子 （1新种子 0不是）
   */
  private Integer newSeedIs;




}
