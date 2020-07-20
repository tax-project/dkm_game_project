package com.dkm.wechat.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qf
 * @date 2020/5/12
 * @vesion 1.0
 **/
@Data
@ApiModel("用户登录")
public class UserLoginVo {

   /**|
    * 用户名
    */
   @ApiModelProperty("账号")
   private String userName;

   /**
    * 密码
    */
   @ApiModelProperty("密码")
   private String password;
}
