package com.dkm.shop.domain;



import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.math.BigDecimal;




/**
 * <p>
 * 每日特惠表
 * </p>
 *
 * @author zy
 * @since 2020-05-09
 */
@TableName("tb_day_cheap")
@Data
public class TbDayCheap extends Model<TbDayCheap> {

    private static final long serialVersionUID = 1L;

    /**
     * 特惠主键
     */
    @TableId("cheap_id")
	private Long cheapId;
	/**
	 * 特惠图片
	 */
	@TableField("cheap_img")
	private String cheapImg;
	/**
	 * 特惠名称
	 */
	@TableField("cheap_name")
	private String cheapName;
	/**
	 * 特惠内容
	 */
	@TableField("cheap_info")
	private String cheapInfo;
	/**
	 * 特惠价格
	 */
	@TableField("cheap_money")
	private BigDecimal cheapMoney;
	/**
	 * 蜂蜜id
	 */
	@TableField("food_id")
	private Long foodId;
	/**
	 * 蜂蜜数量
	 */
	@TableField("food_fm")
	private Integer foodFm;
	/**
	 * 钻石数量
	 */
	@TableField("cheap_dm")
	private Integer cheapDm;
	/**
	 * 金币数量
	 */
	@TableField("cheap_gold")
	private Integer cheapGold;
	/**
	 * 蓝卷id
	 */
	@TableField("lan_id")
	private Long lanId;
	/**
	 * 蓝卷数量
	 */
	@TableField("lan_num")
	private Integer lanNum;
	/**
	 * 鱼的id
	 */
	@TableField("yu_id")
	private Long yuId;
	/**
	 * 鱼的数量
	 */
	@TableField("yu_num")
	private Integer yuNum;
	/**
	 * 闪电id
	 */
	@TableField("sd_id")
	private Long sdId;
	/**
	 * 闪电数量
	 */
	@TableField("sd_num")
	private Integer sdNum;

}
