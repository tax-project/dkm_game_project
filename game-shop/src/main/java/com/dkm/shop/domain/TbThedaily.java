package com.dkm.shop.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;


/**
 * <p>
 * 
 * </p>
 *
 * @author zy
 * @since 2020-06-21
 */
@TableName("tb_thedaily")
@Data
public class TbThedaily extends Model<TbThedaily> {

    private static final long serialVersionUID = 1L;

    /**
     * 每日礼包的主键
     */
	@TableField("thd_id")
	private Long thdId;
    /**
     * 每日礼包名字
     */
	@TableField("thd_name")
	private String thdName;
    /**
     * 打折字段
     */
	@TableField("thd_discount")
	private String thdDiscount;
    /**
     * 商品图片
     */
	@TableField("thd_img")
	private String thdImg;
    /**
     * 需要钻石
     */
	@TableField("thd_money")
	private Integer thdMoney;
    /**
     * 商品详情
     */
	@TableField("thd_content")
	private String thdContent;
    /**
     * 商品详情2
     */
	@TableField("thd_contenttwo")
	private String thdContenttwo;
    /**
     * 1为一天买一次 2为一天二十次
     */
	@TableField("thd_isva")
	private Integer thdIsva;
    /**
     * 拓展字段1
     */
	private String exp1;
    /**
     * 拓展字段2
     */
	private String exp2;





	@Override
	public String toString() {
		return "TbThedaily{" +
			"thdId=" + thdId +
			", thdName=" + thdName +
			", thdDiscount=" + thdDiscount +
			", thdImg=" + thdImg +
			", thdMoney=" + thdMoney +
			", thdContent=" + thdContent +
			", thdContenttwo=" + thdContenttwo +
			", thdIsva=" + thdIsva +
			", exp1=" + exp1 +
			", exp2=" + exp2 +
			"}";
	}
}
