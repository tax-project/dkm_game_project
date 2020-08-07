package com.dkm.shopCart.entities.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 收获地址的实体
 */
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("收获地址的实体")
@TableName("tb_shipping_address")
@Data
public class ShippingAddressInfoVo {
    @ApiModelProperty("ID")
    @TableId("id")
    private Long id;
    @ApiModelProperty("用户的ID")
    @TableField("user_id")
    private Long userId;
    @ApiModelProperty("用户名")
    @TableField("user_name")
    private String name;
    @ApiModelProperty("电话号码")
    @TableField("user_phone")
    private String phone;
    @ApiModelProperty("地址")
    @TableField("user_address")
    private String address;
    @ApiModelProperty("邮编")
    @TableField("user_zip")
    private String zip;
    @ApiModelProperty("是否为默认收货地址")
    @TableField("default_address")
    private Boolean isDefaultAddress;
}
