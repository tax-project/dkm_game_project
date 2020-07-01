package com.dkm.seed.entity.vo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author MQ
 * @PROJECT_NAME: game_project
 * @DESCRIPTION:
 * @DATE: 2020/7/1 16:28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class PirateVo extends Model<PirateVo> {

    /**
     * 昵称
     */
    private String weChatNickName;
    /**
     * 头像地址
     */
    private String weChatHeadImgUrl;

    /**
     *被盗金币
     */
    private Integer pirateLossGold;

    /**
     * 被盗红包
     */
    private BigDecimal pirateLossRed;
}
